package com.server.base.util

import com.server.base.components.constants.Constants
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.platform.commons.util.ReflectionUtils
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import org.modelmapper.ModelMapper
import org.modelmapper.config.Configuration
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import java.lang.reflect.Method
import java.util.*
import java.util.stream.Collectors


@EnableConfigurationProperties
@ExtendWith(MockitoExtension::class)
abstract class AbstractServiceTest {
    @Spy
    open lateinit var mapper: ModelMapper

    open fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }

    @BeforeEach
    open fun setProperties() {
        val constants = Constants()
        try {
            val methods = Arrays.stream<Method>(Constants::class.java.methods)
                    .filter { method: Method ->
                        Arrays.stream<Annotation>(method.annotations)
                                .anyMatch { annotation: Annotation -> annotation.annotationClass == Value::class.java }
                    }
                    .collect(Collectors.toList<Method>())
            for (method in methods) {
                ReflectionUtils.invokeMethod(method, constants, "test")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @BeforeEach
    open fun setModelMapperSet() {
        mapper.configuration
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).isFieldMatchingEnabled = true
    }
}