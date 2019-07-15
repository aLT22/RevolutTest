package co.netmonet.revoluttest.base

import androidx.lifecycle.AndroidViewModel
import co.netmonet.revoluttest.RevolutApp
import io.reactivex.disposables.CompositeDisposable


abstract class BaseAndroidViewModel(
        private val mApp: RevolutApp
) : AndroidViewModel(mApp) {

    protected val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        mCompositeDisposable.clear()

        super.onCleared()
    }
}