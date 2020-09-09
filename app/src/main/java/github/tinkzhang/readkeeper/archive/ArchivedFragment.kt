package github.tinkzhang.readkeeper.archive

import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import github.tinkzhang.readkeeper.R

class ArchivedFragment : Fragment(), github.tinkzhang.readkeeper.archive.OnItemClickListener {

    companion object {
        fun newInstance() = ArchivedFragment()
    }

    private lateinit var viewModel: ArchivedViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_archived, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ArchivedViewModel::class.java)

        val adapter = ArchiveBookListAdapter(this)
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

    override fun onItemClicked(view: View, book: ArchiveBook) {
        Snackbar.make(view, "Hello", Snackbar.LENGTH_LONG).show()
    }

    override fun onItemLongClicked(view: View, book: ArchiveBook) {
        activity?.startActionMode(object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                this@ArchivedFragment.activity?.menuInflater?.inflate(R.menu.list_delete_menu, menu)

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
                    Html.fromHtml(getString(R.string.snackbar_delete_message, book.title), FROM_HTML_MODE_COMPACT),
                    Snackbar.LENGTH_LONG).show()
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                this@ArchivedFragment.recyclerView.adapter?.notifyDataSetChanged()
            }

        })
    }

    override fun onItemImageLongClicked(book: ArchiveBook): Boolean {
        TODO("Not yet implemented")
    }

    override fun onAddButtonClicked(book: ArchiveBook) {
        TODO("Not yet implemented")
    }

}