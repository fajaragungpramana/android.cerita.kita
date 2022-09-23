package com.github.fajaragungpramana.ceritakita.ui.main.story.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.fajaragungpramana.ceritakita.data.constant.DataConstant
import com.github.fajaragungpramana.ceritakita.data.domain.story.StoryUseCase
import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AddStoryViewModel @Inject constructor(private val mStoryUseCase: StoryUseCase) : ViewModel(),
    IAddStoryEvent {

    override fun setStories(storyRequest: StoryRequest): Job = viewModelScope.launch {
        val part = MultipartBody.Builder()
        part.setType(MultipartBody.FORM)

//        part.addPart(RequestBody.create("multipart/form-data".toMediaTypeOrNull(), ))
//        part.addFormDataPart(DataConstant.Http.Param.PHOTO, storyRequest.photo)
//        part.addFormDataPart(DataConstant.Http.Param.DESCRIPTION, storyRequest.description ?: "")
    }

}