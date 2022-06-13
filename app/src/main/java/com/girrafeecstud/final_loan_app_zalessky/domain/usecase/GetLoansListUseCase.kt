package com.girrafeecstud.final_loan_app_zalessky.domain.usecase

import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoanRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoansListUseCase @Inject constructor(
    private val repository: LoanRepositoryImpl
) {
    suspend operator fun invoke(bearerToken: String?): Flow<ApiResult<Any>> {
        return repository.getLoansList(bearerToken = bearerToken)
    }
}