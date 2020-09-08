package github.tinkzhang.readkeeper.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import github.tinkzhang.readkeeper.R
import github.tinkzhang.readkeeper.common.InjectorUtils
import github.tinkzhang.readkeeper.search.model.SearchBook

class SearchResultFragment : Fragment(), OnItemClickListener {

    companion object {
        fun newInstance() = SearchResultFragment()
    }

    private lateinit var viewModel: SearchResultViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private  var keyword: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_result_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, InjectorUtils.provideSearchViewModelFactory(requireContext()))
                .get(SearchResultViewModel::class.java)
        keyword = arguments?.getString("keyword")
        keyword?.let { viewModel.searchBook(it) }

        val adapter: SearchBookListAdapter = SearchBookListAdapter(this )
        viewModel.books.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> progressBar.visibility = View.VISIBLE
                false -> progressBar.visibility = View.GONE
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progressBar)
    }

    override fun onItemClicked(book: SearchBook) {
        TODO("Not yet implemented")
    }

    override fun onItemImageLongClicked(book: SearchBook): Boolean {
        Toast.makeText(context, book.title, Toast.LENGTH_LONG).show()
        return true
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
                Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
            }
            positiveButton(R.string.add)
        }
        }
    }

}