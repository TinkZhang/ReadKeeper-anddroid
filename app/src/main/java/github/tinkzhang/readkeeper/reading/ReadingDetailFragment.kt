package github.tinkzhang.readkeeper.reading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import github.tinkzhang.readkeeper.R
import kotlinx.android.synthetic.main.fragment_reading_detail.*

class ReadingDetailFragment : Fragment() {
    private lateinit var readingBook: ReadingBook
    val viewModel: ReadingViewModel by navGraphViewModels(R.id.mobile_navigation) {
        ReadingViewModelFactory(this.requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reading_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detail_title.text = viewModel.selectedBook?.title
    }

}