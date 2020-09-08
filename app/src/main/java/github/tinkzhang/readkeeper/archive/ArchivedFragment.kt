package github.tinkzhang.readkeeper.archive

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
import github.tinkzhang.readkeeper.search.OnItemClickListener
import github.tinkzhang.readkeeper.search.model.SearchBook

class ArchivedFragment : Fragment(), OnItemClickListener {

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

    override fun onItemClicked(book: SearchBook) {
        TODO("Not yet implemented")
    }

    override fun onItemImageLongClicked(book: SearchBook): Boolean {
        TODO("Not yet implemented")
    }

    override fun onAddButtonClicked(book: SearchBook) {
        TODO("Not yet implemented")
    }

}