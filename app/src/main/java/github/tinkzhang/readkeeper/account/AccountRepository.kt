package github.tinkzhang.readkeeper.account

import androidx.lifecycle.MutableLiveData
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object AccountRepository {
    val RC_SIGN_IN = 1
    var user = MutableLiveData<FirebaseUser>()
    private var auth: FirebaseAuth = Firebase.auth
    init {
        if (auth.currentUser != null) {
            user.value = auth.currentUser
        }
    }
    val authProvider = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.PhoneBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build(),
    )
}