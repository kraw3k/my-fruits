import retrofit2.Response

class FruitRepository {

    suspend fun getFruitResponse(): Response<List<Fruit>> =
        FruitService.fruitService.getFruitResponse()

}