package dev.atahabaki.wordbook.data.listqfs

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object ListQFSSerializer: Serializer<ListQFS> {
    override val defaultValue: ListQFS = ListQFS.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ListQFS {
        try {
            return ListQFS.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto...", exception)
        }
    }

    override suspend fun writeTo(t: ListQFS, output: OutputStream) {
        t.writeTo(output)
    }
}

val Context.listQFSDataStore: DataStore<ListQFS> by dataStore(
    fileName = "listQFS.pb",
    serializer = ListQFSSerializer
)
