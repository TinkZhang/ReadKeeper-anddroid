package github.tinkzhang.readkeeper.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.lapism.search.internal.SearchLayout
import com.lapism.search.widget.MaterialSearchView
import github.tinkzhang.readkeeper.AvatarGenerator
import github.tinkzhang.readkeeper.R
import github.tinkzhang.readkeeper.account.AccountRepository
import github.tinkzhang.readkeeper.account.AccountRepository.RC_SIGN_IN

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
                    materialSearchView.clearFocus()
                    val bundle = bundleOf("keyword" to query.toString())
                    view.findNavController()
                        .navigate(R.id.action_navigation_home_to_searchResultFragment, bundle)
                    return true
                }
            })

            AccountRepository.user.observe(
                viewLifecycleOwner, { user ->
                    if (user == null) {
                        setMenuIconImageResource(R.drawable.ic_settings_24)
                    } else {
//                        user.photoUrl?.let {
//                            val inputStream = context.contentResolver.openInputStream(it)
//                            val avatar = Drawable.createFromStream(inputStream, "Avatar")
//                            setMenuIconImageDrawable(avatar)
//                        } ?: run {
//                            setMenuIconImageDrawable(
//                                AvatarGenerator.avatarImage(
//                                    context,
//                                    16,
//                                    1,
//                                    user.displayName
//                                )
//                            )
//                        }
                        setMenuIconImageDrawable(
                            AvatarGenerator.avatarImage(
                                context,
                                64,
                                user.displayName
                        ))
                    }
                }
            )

            setOnMenuClickListener(object : SearchLayout.OnMenuClickListener {
                override fun onMenuClick() {
                    startActivityForResult(
                        AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(AccountRepository.authProvider)
                            .build(),
                        RC_SIGN_IN
                    )
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

    override fun onStart() {
        super.onStart()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                AccountRepository.user.postValue(FirebaseAuth.getInstance().currentUser)
            } else {
                // TODO: Handle signin failure
            }
        }
    }
}