package com.github.fajaragungpramana.ceritakita.data.remote.story.request

import com.github.fajaragungpramana.ceritakita.data.constant.DataConstant
import com.github.fajaragungpramana.ceritakita.data.extension.removeNulls

data class StoryRequest(
    var page: Int = 0,
    var pageSize: Int = 15,
    var location: Int? = null,
    var photo: String? = null,
    var description: String? = null
) : Map<String, Any> by mapOf(
    DataConstant.Http.Param.PAGE to page,
    DataConstant.Http.Param.SIZE to pageSize,
    DataConstant.Http.Param.LOCATION to location
).removeNulls()