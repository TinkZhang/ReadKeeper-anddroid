package github.tinkzhang.readkeeper.common.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import github.tinkzhang.readkeeper.R

abstract class ListFragment: Fragment(), HasLoading {
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progressBar)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configLoadingBar()
        configRecyclerView()
    }

    abstract fun configRecyclerView()
}