package co.netmonet.revoluttest.data.repository

import co.netmonet.revoluttest.data.RevolutService
import co.netmonet.revoluttest.data.model.network.RateResponse
import io.reactivex.Single


class RevolutRepositoryImpl(
        private val mApi: RevolutService
) : RevolutRepository {

    override fun getLatestRates(base: String): Single<RateResponse> =
            mApi.getLatestRates(base)
}