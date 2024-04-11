package ru.yasdev.domain.requestsDb.models

import android.net.Uri
import kotlinx.serialization.Serializable
import ru.yasdev.domain.requestsDb.serializers.UriAsStringSerializer

@Serializable
sealed interface MultipartFormState: BasicState {
    override val name: String
    @Serializable
    data class Text(val keyValue: KeyValue) : MultipartFormState{
        override val name: String
            get() = "TEXT"
        companion object{
            fun default(): Text{
                return Text(KeyValue.empty())
            }
        }
    }

    @Serializable
    data class BinaryFile(override val uri: @Serializable(UriAsStringSerializer::class) Uri) : MultipartFormState, BasicBinaryFile(){
        override val name: String
            get() = "FILE"
        companion object{
            fun default(): BinaryFile{
                return BinaryFile(Uri.EMPTY)
            }
        }
    }

    override fun isDefault(): Boolean {
        return when(this){
            is BinaryFile -> this == BinaryFile.default()
            is Text -> this == Text.default()
        }
    }
}