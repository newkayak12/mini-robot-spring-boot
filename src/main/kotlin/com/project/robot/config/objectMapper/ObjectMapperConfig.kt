package com.project.robot.config.objectMapper

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(value = "objectMapperConfig")
class ObjectMapperConfig {
    private var objectMapper: ObjectMapper = ObjectMapper();


    private fun deserializeWhenEmptyCase() {
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
    }

    private fun deserializeWhenUnknownCase() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    private fun deserializeWhenEnumCase() {
        objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true)
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
    }

    private fun deserializeRegisterJavaTimeModule() {
        objectMapper.registerModule(JavaTimeModule())
    }

    private fun deserializeSettings() {
        deserializeWhenEnumCase()
        deserializeWhenEmptyCase()
        deserializeWhenUnknownCase()
        deserializeRegisterJavaTimeModule()
    }

    private fun serializeSettings() {
        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
        objectMapper.configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL, true)
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    }
    @Bean
    fun objectMapper () : ObjectMapper {
        this.deserializeSettings()
        this.serializeSettings()
        return this.objectMapper;
    }
}