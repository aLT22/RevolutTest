package co.netmonet.revoluttest.ui

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.netmonet.revoluttest.R
import co.netmonet.revoluttest.base.BaseActivity
import co.netmonet.revoluttest.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityVM>(MainActivityVM::class) {

    private lateinit var mAdapter: RatesListAdapter

    override fun layoutId() = R.layout.activity_main

    override fun initViews() {
        mAdapter = RatesListAdapter(
                { clickedRate, position ->
                    mViewModel.mBaseRate.mName = clickedRate.mName
                    mViewModel.swapRates(mAdapter, position)
                    mBinding.rates.scrollToPosition(0)
                },
                { clickedRate, position, text ->
                    mViewModel.changeAmount(mAdapter, text)
                }
        )

        mBinding.rates.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            adapter = mAdapter
        }
    }

    override fun initListeners() {
    }

    override fun observeChanges() {
        mViewModel.mRatesLiveData.observe(this, Observer {
            mAdapter.submitList(it)
        })

        mViewModel.mLoading.observe(this, Observer {
            mBinding.srl.isRefreshing = it
        })
    }

    override fun removeListeners() {
    }
}
