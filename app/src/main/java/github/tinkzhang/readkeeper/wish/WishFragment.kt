package github.tinkzhang.readkeeper.wish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import github.tinkzhang.readkeeper.R

class WishFragment : Fragment(), OnItemClickListener{

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: WishViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progressBar)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WishViewModel::class.java)

        val adapter = WishBookListAdapter(this)
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