package github.tinkzhang.readkeeper.wish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import github.tinkzhang.readkeeper.R
import github.tinkzhang.readkeeper.common.ui.ListFragment

class WishFragment : ListFragment(), OnItemClickListener{

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

    override fun onItemClicked(view: View, book: WishBook) {
        TODO("Not yet implemented")
    }

    override fun onItemLongClicked(view: View, book: WishBook) {
        TODO("Not yet implemented")
    }

    override fun onItemImageLongClicked(book: WishBook): Boolean {
        TODO("Not yet implemented")
    }

    override fun onAddButtonClicked(book: WishBook) {
        TODO("Not yet implemented")
    }

}