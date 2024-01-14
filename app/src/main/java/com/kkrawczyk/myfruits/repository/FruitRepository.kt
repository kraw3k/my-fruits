import retrofit2.Response

class FruitRepository {

    suspend fun getFruitResponse(): Response<List<Fruit>> =
        FruitService.fruitService.getFruitResponse()

    suspend fun getFruitResponseForId(id: Number): Response<Fruit> =
        FruitService.fruitService.getFruitResponseForId(id)

}