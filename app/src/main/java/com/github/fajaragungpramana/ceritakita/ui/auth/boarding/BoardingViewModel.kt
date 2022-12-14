package com.github.fajaragungpramana.ceritakita.ui.auth.boarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.fajaragungpramana.ceritakita.data.domain.boarding.BoardingUseCase
import com.github.fajaragungpramana.ceritakita.data.extension.flowAsValue
import com.github.fajaragungpramana.ceritakita.data.extension.onResultListener
import com.github.fajaragungpramana.ceritakita.ui.state.BoardingState
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
        _boardingState.send(BoardingState.OnBoardingLoading(true))

        mBoardingUseCase.getListBoarding().onResultListener(
            onSuccess = {
                _boardingState.send(BoardingState.OnBoardingLoading(false))

                _boardingState.send(BoardingState.OnBoardingSuccess(it?.flowAsValue()))
            },
            onFailure = { _, _ ->
                _boardingState.send(BoardingState.OnBoardingLoading(false))

                _boardingState.send(BoardingState.OnBoardingFailure(null))
            },
            onError = {
                _boardingState.send(BoardingState.OnBoardingLoading(false))

                _boardingState.send(BoardingState.OnBoardingFailure(it.message))
            }
        )
    }

}