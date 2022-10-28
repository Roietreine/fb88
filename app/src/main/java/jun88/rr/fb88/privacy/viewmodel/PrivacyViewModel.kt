package jun88.rr.fb88.privacy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jun88.rr.fb88.common.AppModel
import jun88.rr.fb88.privacy.Utils.Companion.privacyDesc
import jun88.rr.fb88.privacy.Utils.Companion.privacyTitle
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PrivacyViewModel : ViewModel() {

    private var privacyList = ArrayList<AppModel>()
    private var privacyInfo = MutableLiveData<List<AppModel>>()
    val prvcyNf : LiveData<List<AppModel>> get() = privacyInfo

    private var privacyError = CoroutineExceptionHandler { _, _ ->
        privacyInfo.postValue(null)
    }

    fun pvcFun() : MutableLiveData<List<AppModel>>{
        viewModelScope.launch(privacyError + Dispatchers.IO){
            for (n in privacyTitle.indices){
                privacyList.add(AppModel(privacyTitle[n], privacyDesc[n]))
            }
            privacyInfo.postValue(privacyList)
        }
        return privacyInfo
    }
}