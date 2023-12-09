data class Fruit(
    val name: String,
    val genus: String,
    val id: Number,
    val family: String,
    val order: String,
    val nutritions: FruitNutritions
)

data class FruitNutritions(
    val carbohydrates: Number,
    val protein: Number,
    val fat: Number,
    val calories: Number,
    val sugar: Number
)