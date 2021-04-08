package github.tinkzhang.readkeeper.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.google.android.material.snackbar.Snackbar
import github.tinkzhang.readkeeper.R
import github.tinkzhang.readkeeper.common.InjectorUtils
import github.tinkzhang.readkeeper.common.ui.ListFragment
import github.tinkzhang.readkeeper.search.model.SearchBook

class SearchResultFragment : ListFragment(), SearchCardInteraction {

    private lateinit var viewModel: SearchResultViewModel
    private  var keyword: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_result_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this, InjectorUtils.provideSearchViewModelFactory(requireContext()))
                .get(SearchResultViewModel::class.java)
        keyword = arguments?.getString("keyword")
        keyword?.let { viewModel.searchBook(it) }

        super.onActivityCreated(savedInstanceState)
    }

    override fun configRecyclerView() {
        val adapter = SearchBookListAdapter(this )
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        viewModel.books.observe(this, {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

    override fun configLoadingBar() {
        with(viewModel) {
            with(isLoading) {
                observe(viewLifecycleOwner, Observer {
                    when (it) {
                        true -> progressBar.visibility = View.VISIBLE
                        false -> progressBar.visibility = View.GONE
                    }
                })
            }
        }
    }

    override fun onAddButtonClicked(book: SearchBook) {
        context?.let {
            MaterialDialog(it).show {
            listItemsSingleChoice(R.array.add_destinations) { dialog, index, text ->
                val toastText = context.getString(R.string.add_to, book.title, text)
                when(index) {
                    0 -> viewModel.addToReading(book)
                    1 -> viewModel.addToWish(book)
                    2 -> viewModel.addToArchive(book)
                }
                Snackbar.make(view, book.title, Snackbar.LENGTH_LONG).show()
            }
                positiveButton(R.string.add)
            }
        }
    }

}