import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val fruitRepository = FruitRepository()

    private val mutableFruitsData = MutableLiveData<List<Fruit>>()
    val immutableFruitsData: LiveData<List<Fruit>> = mutableFruitsData

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = fruitRepository.getFruitResponse()
                val fruits = request.body()
                mutableFruitsData.postValue(fruits)

            } catch (e: Exception) {
                Log.e("MainViewModel", "Operacja nie powiodla sie", e)
            }
        }
    }
}