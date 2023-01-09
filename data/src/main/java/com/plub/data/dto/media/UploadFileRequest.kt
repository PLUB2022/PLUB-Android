package com.plub.data.dto.media

import com.plub.data.base.DataDto
import java.io.File

data class UploadFileRequest(
    val type:String,
    val file:File,
): DataDto