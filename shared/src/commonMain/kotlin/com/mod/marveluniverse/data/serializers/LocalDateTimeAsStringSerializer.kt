package com.mod.marveluniverse.data.serializers

import kotlinx.datetime.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object LocalDateTimeAsStringSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val string = value.toInstant(TimeZone.UTC).toString()
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        val stringValue = decoder.decodeString()
        if (stringValue.isEmpty()
            || stringValue.startsWith("-")) return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        return stringValue.substring(0, 19).replace(" ", "T").toLocalDateTime()
    }
}
