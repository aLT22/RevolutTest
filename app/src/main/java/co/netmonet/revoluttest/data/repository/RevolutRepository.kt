package co.netmonet.revoluttest.data.repository

import co.netmonet.revoluttest.data.model.network.RateResponse
import io.reactivex.Single


interface RevolutRepository : Repository {

    fun getLatestRates(base: String = "EUR"): Single<RateResponse>

}