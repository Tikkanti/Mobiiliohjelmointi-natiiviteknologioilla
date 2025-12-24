/*package com.example.weatherapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.LocationLiveData


class LocationViewModel(context: Context): ViewModel() {
    private val locationLiveData= LocationLiveData(context)
    fun getLocationLiveData() = locationLiveData
}


 */

package com.example.weatherapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.model.LocationLiveData

class LocationViewModel(context: Context): ViewModel() {
    private val locationLiveData = LocationLiveData(context)
    fun getLocationLiveData() = locationLiveData

    // ADD: error-tila
    private val errorLiveData = MutableLiveData<String?>()
    fun getErrorLiveData(): LiveData<String?> = errorLiveData

    // ADD: loading-tila
    private val loadingLiveData = MutableLiveData(false)
    fun getLoadingLiveData(): LiveData<Boolean> = loadingLiveData

    // ADD: apumetodit tilojen hallintaan
    fun setLoading(isLoading: Boolean) {
        loadingLiveData.value = isLoading
    }

    fun setError(message: String?) {
        errorLiveData.value = message
    }

    fun clearError() {
        errorLiveData.value = null
    }
}
