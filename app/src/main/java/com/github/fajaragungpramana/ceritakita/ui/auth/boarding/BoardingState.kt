package com.github.fajaragungpramana.ceritakita.ui.auth.boarding

import com.github.fajaragungpramana.ceritakita.data.local.boarding.model.Boarding

sealed class BoardingState {
    data class OnBoardingLoading(val isLoading: Boolean) : BoardingState()
    data class OnBoardingSuccess(val listBoarding: List<Boarding>?) : BoardingState()
    data class OnBoardingFailure(val message: String?) : BoardingState()
}
