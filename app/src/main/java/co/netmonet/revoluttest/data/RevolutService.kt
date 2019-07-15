package co.netmonet.revoluttest.data

import co.netmonet.revoluttest.data.model.network.RateResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface RevolutService {

    @GET("latest")
    fun getLatestRates(@Query("base") base: String = "EUR"): Single<RateResponse>

}