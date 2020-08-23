package github.tinkzhang.readkeeper.wish

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import github.tinkzhang.readkeeper.R

class WishFragment : Fragment() {

    companion object {
        fun newInstance() = WishFragment()
    }

    private lateinit var viewModel: WishViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wish, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WishViewModel::class.java)
        // TODO: Use the ViewModel
    }

}