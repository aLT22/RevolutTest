package co.netmonet.revoluttest.ui

import androidx.lifecycle.MutableLiveData
import co.netmonet.revoluttest.RevolutApp
import co.netmonet.revoluttest.base.BaseAndroidViewModel
import co.netmonet.revoluttest.data.model.Rate
import co.netmonet.revoluttest.data.repository.RevolutRepository
import co.netmonet.revoluttest.utils.ext.extendedErrorMessage
import co.netmonet.revoluttest.utils.ext.performOnBackOutOnMain
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivityVM(
        private val mApp: RevolutApp,
        private val mRepository: RevolutRepository
) : BaseAndroidViewModel(mApp) {

    val mRatesLiveData = MutableLiveData<List<Rate>>()

    val mLoading = MutableLiveData<Boolean>()
    private var mIsFirstLoading = true

    var mNames = LinkedList<String>()
    var mCurrencies = LinkedList<Double>()
    var mRates = LinkedList<Rate>()

    var mValue: Double = 0.0

    var mBaseRate: Rate = Rate("EUR", 1.0, mValue)

    init {
        mCompositeDisposable.add(getLatestRates())
    }

    private fun getLatestRates() =
            Observable
                    .interval(1, TimeUnit.SECONDS)
                    .flatMap {
                        return@flatMap mRepository
                                .getLatestRates(mBaseRate.mName)
                                .extendedErrorMessage()
                                .doOnSubscribe {
                                    mLoading.postValue(mIsFirstLoading)
                                }
                                .doOnSuccess {
                                    mIsFirstLoading = false
                                    mLoading.postValue(mIsFirstLoading)
                                }
                                .toObservable()
                    }
                    .map { rateResponse ->
                        mNames.clear()
                        rateResponse.mRates.keys.forEach {
                            mNames.add(it)
                        }
                        mCurrencies.clear()
                        rateResponse.mRates.values.forEach {
                            mCurrencies.add(it)
                        }

                        mRates = LinkedList()
                        mBaseRate.mAmount = mValue
                        mRates.add(mBaseRate)
                        mNames.forEachIndexed { index, s ->
                            mRates.add(
                                    Rate(
                                            s,
                                            mCurrencies[index],
                                            mValue * mCurrencies[index]
                                    )
                            )
                        }

                        return@map mRates
                    }
                    .extendedErrorMessage()
                    .performOnBackOutOnMain()
                    .subscribe(
                            { rates ->
                                mRatesLiveData.value = rates
                            },
                            {
                                it.printStackTrace()
                            }
                    )

    fun swapRates(adapter: RatesListAdapter, oldPosition: Int) {
        adapter.notifyItemMoved(oldPosition, 0)
        adapter.notifyItemChanged(oldPosition)
        adapter.notifyItemChanged(0)
    }

    fun changeAmount(adapter: RatesListAdapter, value: String) {
        mValue = try {
            if (value.isNotEmpty() && value != ".") {
                value.toDouble()
            } else {
                0.0
            }
        } catch (nfe: NumberFormatException) {
            value.replace(".", "").toDouble()
        }
        mRates.forEachIndexed { index, rate ->
            if (index != 0) {
                if (value.isBlank() || value == ".") {
                    rate.mAmount = 0.0
                } else {
                    rate.mAmount = rate.mCurrency * mValue
                }
            } else {
                rate.mAmount = mValue
            }
        }
        adapter.submitList(mRates)
    }

    companion object {
        const val TAG = "MainActivityVM"
    }

}