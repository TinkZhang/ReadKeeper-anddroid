package github.tinkzhang.readkeeper.reading

import android.os.Bundle
import android.text.Html
import android.view.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import github.tinkzhang.readkeeper.R
import github.tinkzhang.readkeeper.common.ui.ListFragment

class ReadingFragment : ListFragment(), ReadingCardInteraction {

    val viewModel: ReadingViewModel by navGraphViewModels(R.id.mobile_navigation) {
        ReadingViewModelFactory(this.requireActivity().application)
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reading, container, false)
    }

    override fun configRecyclerView() {
        val adapter = ReadingBookListAdapter(this)
        viewModel.books.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    override fun configLoadingBar() {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            when (it) {
                true -> progressBar.visibility = View.VISIBLE
                false -> progressBar.visibility = View.GONE
            }
        })
    }

    override fun onItemLongClicked(view: View, book: ReadingBook) {
        activity?.startActionMode(object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                this@ReadingFragment.activity?.menuInflater?.inflate(R.menu.list_delete_menu, menu)

                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.title = book.title
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, menu: MenuItem?): Boolean {
                when(menu?.itemId) {
                    R.id.delete_menu -> viewModel.delete(book)
                }
                mode?.finish()
                Snackbar.make(
                        view,
                        Html.fromHtml(getString(R.string.snackbar_delete_message, book.title), Html.FROM_HTML_MODE_COMPACT),
                        Snackbar.LENGTH_LONG).show()
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                this@ReadingFragment.recyclerView.adapter?.notifyDataSetChanged()
            }

        })
    }

    override fun onItemClicked(view: View, book: ReadingBook) {
        super.onItemClicked(view, book)
        viewModel.selectedBookIndex = viewModel.books.value?.indexOf(book)
        findNavController().navigate(R.id.action_navigation_reading_to_readingDetailFragment)
    }
}