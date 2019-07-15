package co.netmonet.revoluttest.ui

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import co.netmonet.revoluttest.R
import co.netmonet.revoluttest.base.BaseViewHolder
import co.netmonet.revoluttest.data.model.Rate
import co.netmonet.revoluttest.databinding.ItemRateBinding
import java.text.DecimalFormat


private typealias OnRateClick = (rate: Rate, position: Int) -> Unit
private typealias OnRateChangeCallback = (rate: Rate, position: Int, text: String) -> Unit

class RatesListAdapter(
        private val mOnRateClickListener: OnRateClick,
        private val mOnRateChangeCallback: OnRateChangeCallback
) : ListAdapter<Rate, RatesListAdapter.RateViewHolder>(RateItemDiffCallback()) {

    private val formatter = DecimalFormat("#0.00")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder =
            RateViewHolder(
                    DataBindingUtil
                            .inflate(
                                    LayoutInflater.from(parent.context),
                                    R.layout.item_rate,
                                    parent,
                                    false
                            )
            )

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.bind(position, getItem(position), null)
    }

    inner class RateViewHolder(
            private val mBinding: ItemRateBinding
    ) : BaseViewHolder<ItemRateBinding, Rate>(mBinding) {

        private val baseTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (adapterPosition == 0) {
                    mOnRateChangeCallback
                            .invoke(
                                    this@RatesListAdapter.getItem(adapterPosition),
                                    adapterPosition,
                                    s.toString()
                            )
                }
            }
        }

        override fun bind(position: Int, model: Rate, listener: ((Rate) -> Unit)?) {
            super.bind(position, model, listener)

            if (adapterPosition == 0)
                mBinding.value.addTextChangedListener(baseTextWatcher)
            else
                mBinding.value.removeTextChangedListener(baseTextWatcher)

            mBinding.root.setOnClickListener {
                mOnRateClickListener.invoke(model, position)
            }

            mBinding.value.isEnabled = position == 0

            mBinding.value.setText(formatter.format(model.mAmount))
        }
    }
}

private class RateItemDiffCallback : DiffUtil.ItemCallback<Rate>() {

    override fun areItemsTheSame(oldItem: Rate, newItem: Rate): Boolean =
            oldItem.mName == newItem.mName

    override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean =
            oldItem == newItem

    companion object {
        const val TAG = "RateItemDiffCallback"
    }

}