package com.server.base.util

import com.server.base.restDoc.Config
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter


@SpringBootTest
@AutoConfigureMockMvc
@EnableConfigurationProperties
@ActiveProfiles(value = ["local"])
@Import(Config::class)
@ExtendWith(RestDocumentationExtension::class)
open abstract class AbstractControllerTest {
    /**
     * <h3> JSONPath 사용법 </h3>
     * <pre>
     *  기본 문법 요소
     * JSON의 데이터는 키-값 쌍으로 이루어진 요소들을 객체(object; {}로 표현) 또는 배열(array; []로 표현)로 묶어놓은 형태를 가진다.
     * 데이터 구조가 간단하다 보니 이를 처리하는 JSONPath의 기본 문법 요소도 비교적 간단하게 구성되어 있다. 자주 쓰이는 문법 요소들은 다음과 같다.
     * $ : 루트 노드. JSONPath의 모든 표현식은 이것으로 시작된다.
     * @ : 현재 노드. 아래에서 소개할 조건부 필터 표현식에서 사용된다.
     * . : 하위 노드
     * .. : 중첩된 전체 하위 요소들(nested descendants)
     * [] : 배열 인덱스
     * * : 모든 요소와 매칭되는 와일드 카드
     * ?(boolean expression) : 조건부 필터 표현식
     * </pre>
     */

    @Autowired
    open lateinit var restDocs: RestDocumentationResultHandler

    @Autowired
    open lateinit var mockMvc: MockMvc

    @BeforeEach
    open fun setUp(context: WebApplicationContext?, restDocumentation: RestDocumentationContextProvider?) {
        println("::::::: SET UP REST DOC ::::::")
        mockMvc = MockMvcBuilders.webAppContextSetup(context!!)
                .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
                .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
                .alwaysDo<DefaultMockMvcBuilder>(restDocs)
                .addFilters<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
                .build()
    }
}