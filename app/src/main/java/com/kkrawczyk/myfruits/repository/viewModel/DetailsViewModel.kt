import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    private val fruitRepository = FruitRepository()

    private val mutableFruitData = MutableLiveData<UiState<Fruit>>()
    val immutableFruitData: LiveData<UiState<Fruit>> = mutableFruitData

    data class UiState<T>(
        val data: T? = null,
        val isLoading: Boolean = true,
        val error: String? = null
    )

    fun getData(id: Number) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = fruitRepository.getFruitResponseForId(id)
                val fruit = request.body()
                mutableFruitData.postValue(UiState(isLoading = false, data = fruit, error = null))

            } catch (e: Exception) {
                mutableFruitData.postValue(
                    UiState(
                        isLoading = false,
                        data = null,
                        error = e.message
                    )
                )
                Log.e("DetailsViewModel", "Error occurred", e)
            }
        }
    }

}