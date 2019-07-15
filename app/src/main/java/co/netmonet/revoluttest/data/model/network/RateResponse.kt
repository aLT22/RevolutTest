package co.netmonet.revoluttest.data.model.network


import com.google.gson.annotations.SerializedName

data class RateResponse(
        @SerializedName("base")
        val mBase: String = "",
        @SerializedName("date")
        val mDate: String = "",
        @SerializedName("rates")
        val mRates: Map<String, Double>
)