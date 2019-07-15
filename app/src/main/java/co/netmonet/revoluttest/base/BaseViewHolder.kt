package co.netmonet.revoluttest.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.netmonet.revoluttest.BR
import org.koin.standalone.KoinComponent


open class BaseViewHolder<V : ViewDataBinding, M : Any>(
        private val mBinding: V
) : RecyclerView.ViewHolder(mBinding.root), KoinComponent {

    open fun bind(position: Int, model: M, listener: ((M) -> Unit)?) {
        mBinding.setVariable(BR.model, model)

        mBinding.root.setOnClickListener {
            listener?.invoke(model)
        }
    }

}