package com.github.fajaragungpramana.ceritakita.ui.auth.boarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.fajaragungpramana.ceritakita.data.domain.boarding.BoardingUseCase
import com.github.fajaragungpramana.ceritakita.data.extension.flowAsValue
import com.github.fajaragungpramana.ceritakita.data.extension.onResultListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardingViewModel @Inject constructor(private val mBoardingUseCase: BoardingUseCase) :
    ViewModel(), IBoardingEvent {

    private val _boardingState by lazy { Channel<BoardingState>() }
    val boardingState: Flow<BoardingState>
        get() = _boardingState.receiveAsFlow()

    override fun getListBoarding(): Job = viewModelScope.launch {
        mBoardingUseCase.getListBoarding().onResultListener(
            onLoading = {
                _boardingState.send(BoardingState.OnBoardingLoading(it))
            },
            onSuccess = {
                _boardingState.send(BoardingState.OnBoardingSuccess(it?.flowAsValue()))
            },
            onFailure = {
                _boardingState.send(BoardingState.OnBoardingFailure(null))
            },
            onError = {
                _boardingState.send(BoardingState.OnBoardingFailure(it.message))
            }
        )
    }

}