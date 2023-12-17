import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val fruitRepository = FruitRepository()

    private val mutableFruitsData = MutableLiveData<UiState<List<Fruit>>>()
    val immutableFruitsData: LiveData<UiState<List<Fruit>>> = mutableFruitsData

    data class UiState<T>(
        val data: T? = null,
        val isLoading: Boolean = true,
        val error: String? = null
    )

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = fruitRepository.getFruitResponse()
                val fruits = request.body()
                mutableFruitsData.postValue(UiState(isLoading = false, data = fruits, error = null))

            } catch (e: Exception) {
                mutableFruitsData.postValue(
                    UiState(
                        isLoading = false,
                        data = null,
                        error = e.message
                    )
                )
                Log.e("MainViewModel", "Error occurred", e)
            }
        }
    }
}