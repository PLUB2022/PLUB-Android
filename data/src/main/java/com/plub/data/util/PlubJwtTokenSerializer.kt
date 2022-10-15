@file:Suppress("BlockingMethodInNonBlockingContext")

package com.plub.data.util

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class PlubJwtTokenSerializer @Inject constructor(
    private val crypto: Crypto
): Serializer<PlubJwtToken> {
    override val defaultValue: PlubJwtToken get() = PlubJwtToken.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): PlubJwtToken {
        return try {
            PlubJwtToken.parseFrom(
                input
                    .readBytes()
                    .let(crypto::decrypt)
            )
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override suspend fun writeTo(t: PlubJwtToken, output: OutputStream) {
        output.write(crypto.encrypt(t.toByteArray()))
        output.flush()
    }
}