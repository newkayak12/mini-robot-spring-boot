package com.server.base.util

import com.server.base.components.configure.queryDsl.Config
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.*
import org.springframework.data.repository.config.BootstrapMode
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@DataJpaTest(bootstrapMode = BootstrapMode.DEFAULT, showSql = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(includeFilters = [ComponentScan.Filter(type = FilterType.ANNOTATION, classes = [Configuration::class, Repository::class])])
@Profile("local")
@Import(Config::class)
open abstract class AbstractRepositoryTest {
    @Autowired
    open lateinit var entityManager: EntityManager
}