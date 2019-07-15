package co.netmonet.revoluttest.base

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import co.netmonet.revoluttest.BR
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModelByClass
import kotlin.reflect.KClass

abstract class BaseActivity<V : ViewDataBinding, out VM : BaseAndroidViewModel>(
        clazz: KClass<out VM>
) : AppCompatActivity() {

    protected lateinit var mBinding: V
    protected val mViewModel: VM by viewModelByClass(clazz)
    protected lateinit var mCompositeDisposable: CompositeDisposable

    @LayoutRes
    abstract fun layoutId(): Int

    abstract fun initViews()
    abstract fun initListeners()
    abstract fun observeChanges()
    abstract fun removeListeners()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mCompositeDisposable = CompositeDisposable()

        mBinding = DataBindingUtil.setContentView(this, layoutId())
        mBinding.lifecycleOwner = this
        mBinding.setVariable(BR.vm, mViewModel)

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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
            when (item?.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> {
                    super.onOptionsItemSelected(item)
                }
            }

}