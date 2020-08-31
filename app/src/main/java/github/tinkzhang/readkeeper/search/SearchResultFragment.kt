package github.tinkzhang.readkeeper.search

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

class SearchResultFragment : Fragment() {

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
        viewModel = ViewModelProvider(this).get(SearchResultViewModel::class.java)
        keyword = arguments?.getString("keyword")
        keyword?.let { viewModel.searchBook(it) }

        val adapter: SearchBookListAdapter = SearchBookListAdapter()
        viewModel.books.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            when(it){
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

}