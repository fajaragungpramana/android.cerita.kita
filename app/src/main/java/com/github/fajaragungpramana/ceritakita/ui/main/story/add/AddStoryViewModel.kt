package com.github.fajaragungpramana.ceritakita.ui.main.story.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.fajaragungpramana.ceritakita.data.constant.DataConstant
import com.github.fajaragungpramana.ceritakita.data.domain.story.StoryUseCase
import com.github.fajaragungpramana.ceritakita.data.extension.flowAsValue
import com.github.fajaragungpramana.ceritakita.data.extension.onResultListener
import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import com.github.fajaragungpramana.ceritakita.ui.state.AddStoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject

@HiltViewModel
class AddStoryViewModel @Inject constructor(private val mStoryUseCase: StoryUseCase) : ViewModel(),
    IAddStoryEvent {

    private val _addStoryState by lazy { Channel<AddStoryState>() }
    val addStoryState: Flow<AddStoryState>
        get() = _addStoryState.receiveAsFlow()

    override fun setStories(storyRequest: StoryRequest): Job = viewModelScope.launch {
        val photo = storyRequest.photo

        val part = MultipartBody.Builder()
        part.setType(MultipartBody.FORM)

        if (photo != null)
            part.addFormDataPart(
                DataConstant.Http.Param.PHOTO,
                storyRequest.photo?.name,
                photo.asRequestBody("image/*".toMediaTypeOrNull())
            )
        part.addFormDataPart(
            DataConstant.Http.Param.DESCRIPTION,
            storyRequest.description ?: ""
        )

        _addStoryState.send(AddStoryState.OnAddStoryLoading(true))
        mStoryUseCase.setStories(part.build()).onResultListener(
            onSuccess = {
                _addStoryState.send(AddStoryState.OnAddStoryLoading(false))
                _addStoryState.send(AddStoryState.OnAddStorySuccess(it?.flowAsValue()))
            },
            onFailure = { _, data ->
                _addStoryState.send(AddStoryState.OnAddStoryLoading(false))
                _addStoryState.send(AddStoryState.OnAddStoryFailure(data?.flowAsValue()?.responseMessage))
            },
            onError = {
                _addStoryState.send(AddStoryState.OnAddStoryLoading(false))
                _addStoryState.send(AddStoryState.OnAddStoryFailure(it.message))
            }
        )
    }

}