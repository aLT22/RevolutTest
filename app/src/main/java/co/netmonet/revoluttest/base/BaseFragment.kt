package co.netmonet.revoluttest.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModelByClass
import kotlin.reflect.KClass


abstract class BaseFragment<V : ViewDataBinding, out VM : BaseAndroidViewModel>(
        clazz: KClass<VM>
) : Fragment() {

    protected lateinit var mBinding: V
    protected val mViewModel: VM by viewModelByClass(clazz)
    protected lateinit var mCompositeDisposable: CompositeDisposable

    @LayoutRes
    abstract fun layoutId(): Int

    abstract fun initViews()
    abstract fun initListeners()
    abstract fun observeChanges()
    abstract fun removeListeners()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        mBinding.lifecycleOwner = this
//        mBinding.setVariable(BR.vm, mViewModel)

        mCompositeDisposable = CompositeDisposable()

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    override fun onStart() {
        super.onStart()

        initListeners()
        observeChanges()
    }

    override fun onStop() {
        removeListeners()
        mCompositeDisposable.clear()

        super.onStop()
    }
}