package github.tinkzhang.readkeeper.reading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import github.tinkzhang.readkeeper.R

class ReadingFragment : Fragment() {

    private lateinit var readingViewModel: ReadingViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        readingViewModel =
                ViewModelProvider(this).get(ReadingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_reading, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        readingViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}