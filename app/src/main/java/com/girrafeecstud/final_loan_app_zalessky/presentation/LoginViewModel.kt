package com.girrafeecstud.final_loan_app_zalessky.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.repository.BearerTokenParserRepository
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginSharedPreferencesRepositoryImpl
import com.girrafeecstud.final_loan_app_zalessky.domain.usecase.LoginUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loginSharedPreferencesRepositoryImpl: LoginSharedPreferencesRepositoryImpl,
    private val bearerTokenParserRepository: BearerTokenParserRepository
): ViewModel() {

    private val isConnecting = MutableLiveData<Boolean>()

    private val loginResult = MutableLiveData<ApiResult<Any>>()

    init {
        //isConnecting.value = false
    }

    private fun makeConnectionStatusTrue() {
        isConnecting.value = true
    }

    private fun makeConnectionStatusFalse() {
        isConnecting.value = false
    }

    fun getConnectionStatus(): LiveData<Boolean> {
        return isConnecting
    }

    fun getLoginResult(): LiveData<ApiResult<Any>> {
        return loginResult
    }

    fun login(userName: String, userPassword: String) {
        viewModelScope.launch {
            loginUseCase(userName = userName, userPassword = userPassword)
                .onStart {
                    makeConnectionStatusTrue()
                }
                .collect { result ->
                    loginResult.value = result
                    if (result is ApiResult.Error)
                        makeConnectionStatusFalse()
                }
        }
    }

    fun setUserAuthorizedStatusWithToken(userBearerToken: String) {
        viewModelScope.launch {
            async {
                loginSharedPreferencesRepositoryImpl.setUserAuthorized()
                Log.i("tag log vm", "save")
            }
            async {
                loginSharedPreferencesRepositoryImpl.setUserBearerToken(
                    userBearerToken = bearerTokenParserRepository.parseBearerToken(userBearerToken = userBearerToken)
                )
                // TODO СОХРАНЯТЬ ИМЯ В shared
            }
        }
    }

}