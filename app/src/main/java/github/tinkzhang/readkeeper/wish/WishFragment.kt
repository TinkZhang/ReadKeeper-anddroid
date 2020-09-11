package github.tinkzhang.readkeeper.wish

import android.os.Bundle
import android.text.Html
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import github.tinkzhang.readkeeper.R
import github.tinkzhang.readkeeper.common.ui.ListFragment

class WishFragment : ListFragment(), WishCardInteraction {

    private lateinit var viewModel: WishViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wish, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(WishViewModel::class.java)
        super.onActivityCreated(savedInstanceState)
    }

    override fun configRecyclerView() {
        val adapter = WishBookListAdapter(this)
        viewModel.books.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    override fun configLoadingBar() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> progressBar.visibility = View.VISIBLE
                false -> progressBar.visibility = View.GONE
            }
        })
    }

    override fun onItemLongClicked(view: View, book: WishBook) {
        activity?.startActionMode(object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                this@WishFragment.activity?.menuInflater?.inflate(R.menu.list_delete_menu, menu)

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
                this@WishFragment.recyclerView.adapter?.notifyDataSetChanged()
            }

        })
    }

}