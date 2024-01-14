import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface FruitService {

    @GET("/api/fruit/all")
    suspend fun getFruitResponse(): Response<List<Fruit>>

    @GET("/api/fruit/{id}")
    suspend fun getFruitResponseForId(@Path("id") id: Number): Response<Fruit>

    companion object {
        private const val FRUITYVICE_URL = "https://www.fruityvice.com/"

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(FRUITYVICE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val fruitService: FruitService by lazy {
            retrofit.create(FruitService::class.java)
        }
    }
}