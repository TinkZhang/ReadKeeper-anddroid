package github.tinkzhang.readkeeper.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.lapism.search.internal.SearchLayout
import com.lapism.search.widget.MaterialSearchView
import github.tinkzhang.readkeeper.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var materialSearchView: MaterialSearchView


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configSearch(view)
    }

    private fun configSearch(view: View) {
        materialSearchView = view.findViewById(R.id.material_search_view)
        materialSearchView.apply {

            setTextHint(getString(R.string.search))

            navigationIconSupport = SearchLayout.NavigationIconSupport.SEARCH
            setOnNavigationClickListener(object : SearchLayout.OnNavigationClickListener {
                override fun onNavigationClick() {
                    materialSearchView.requestFocus()
                }
            })

            setOnQueryTextListener(object : SearchLayout.OnQueryTextListener {
                override fun onQueryTextChange(newText: CharSequence): Boolean {
//                    adapter.filter(newText)
                    return true
                }

                override fun onQueryTextSubmit(query: CharSequence): Boolean {
                    val bundle = bundleOf("keyword" to query.toString())
                    view.findNavController()
                            .navigate(R.id.action_navigation_home_to_searchResultFragment, bundle)
                    return true
                }
            })

            setMenuIconImageResource(R.drawable.ic_settings_24)
            setOnMenuClickListener(object : SearchLayout.OnMenuClickListener {
                override fun onMenuClick() {

                }
            })

            setBackgroundStrokeWidth(resources.getDimensionPixelSize(R.dimen.search_stroke_width))
            setBackgroundStrokeColor(
                ContextCompat.getColor(
                    context,
                    R.color.purple_200
                )
            )

            setOnFocusChangeListener(object : SearchLayout.OnFocusChangeListener {
                override fun onFocusChange(hasFocus: Boolean) {
                    navigationIconSupport = if (hasFocus) {
                        SearchLayout.NavigationIconSupport.ARROW
                    } else {
                        SearchLayout.NavigationIconSupport.SEARCH
                    }
                }
            })

        }
    }
}