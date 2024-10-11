package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.logging.Level


data class Answer (
    @SerializedName("id"                   ) var id                   : Int?               = null,
    @SerializedName("title"                ) var title                : String?            = null,
    @SerializedName("description"          ) var description          : String?            = null,
    @SerializedName("category"             ) var category             : String?            = null,
    @SerializedName("price"                ) var price                : Double?            = null,
    @SerializedName("discountPercentage"   ) var discountPercentage   : Double?            = null,
    @SerializedName("rating"               ) var rating               : Double?            = null,
    @SerializedName("stock"                ) var stock                : Int?               = null,
    @SerializedName("tags"                 ) var tags                 : ArrayList<String>  = arrayListOf(),
    @SerializedName("brand"                ) var brand                : String?            = null,
    @SerializedName("sku"                  ) var sku                  : String?            = null,
    @SerializedName("weight"               ) var weight               : Int?               = null,
    @SerializedName("dimensions"           ) var dimensions           : Dimensions?        = Dimensions(),
    @SerializedName("warrantyInformation"  ) var warrantyInformation  : String?            = null,
    @SerializedName("shippingInformation"  ) var shippingInformation  : String?            = null,
    @SerializedName("availabilityStatus"   ) var availabilityStatus   : String?            = null,
    @SerializedName("reviews"              ) var reviews              : ArrayList<Reviews> = arrayListOf(),
    @SerializedName("returnPolicy"         ) var returnPolicy         : String?            = null,
    @SerializedName("minimumOrderQuantity" ) var minimumOrderQuantity : Int?               = null,
    @SerializedName("meta"                 ) var meta                 : Meta?              = Meta(),
    @SerializedName("images"               ) var images               : ArrayList<String>  = arrayListOf(),
    @SerializedName("thumbnail"            ) var thumbnail            : String?            = null
)

data class Dimensions (
    @SerializedName("width"  ) var width  : Double? = null,
    @SerializedName("height" ) var height : Double? = null,
    @SerializedName("depth"  ) var depth  : Double? = null
)

data class Reviews (
    @SerializedName("rating"        ) var rating        : Int?    = null,
    @SerializedName("comment"       ) var comment       : String? = null,
    @SerializedName("date"          ) var date          : String? = null,
    @SerializedName("reviewerName"  ) var reviewerName  : String? = null,
    @SerializedName("reviewerEmail" ) var reviewerEmail : String? = null
)

data class Meta (
    @SerializedName("createdAt" ) var createdAt : String? = null,
    @SerializedName("updatedAt" ) var updatedAt : String? = null,
    @SerializedName("barcode"   ) var barcode   : String? = null,
    @SerializedName("qrCode"    ) var qrCode    : String? = null

)
interface ProductApi {
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Answer
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit =
            Retrofit.Builder().baseUrl("https://dummyjson.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build()

        val productApi = retrofit.create(ProductApi::class.java)

        GlobalScope.launch {
            productApi.getProductById(12).title?.let { Log.d("RRR", it) }
        }

     /*   productApi.getProductById(12).enqueue(object: Callback<Answer>{
            override fun onResponse(call: Call<Answer>, response: Response<Answer>) {
                response.body()?.title?.let { Log.d("RRR", it) }
            }

            override fun onFailure(call: Call<Answer>, t: Throwable) {

            }
        })*/
    }
}