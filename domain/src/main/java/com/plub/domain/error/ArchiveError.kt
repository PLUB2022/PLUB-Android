package com.plub.domain.error

sealed class ArchiveError: IndividualError() {

    companion object{
        private const val NOT_FOUND_ARCHIVE = 3500
        private const val NOT_ARCHIVE_AUTHOR = 3510

        fun make(code:Int) : ArchiveError {
            return when(code) {
                NOT_FOUND_ARCHIVE -> NotFoundArchive("아카이브가 디비에 없음")
                NOT_ARCHIVE_AUTHOR -> NotArchiveAuthor("아카이브 작성자가 아님")
                else -> Common
            }
        }
    }

    data class NotFoundArchive(val msg:String): ArchiveError()
    data class NotArchiveAuthor(val msg:String): ArchiveError()
    object Common : ArchiveError()
}
