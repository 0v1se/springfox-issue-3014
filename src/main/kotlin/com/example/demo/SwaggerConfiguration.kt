package com.example.demo

import com.fasterxml.classmate.TypeResolver
import com.fasterxml.jackson.databind.node.ObjectNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.schema.AlternateTypeRules
import springfox.documentation.schema.WildcardType
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux

@EnableSwagger2WebFlux
class SwaggerConfiguration {

    @Autowired
    private lateinit var resolver: TypeResolver

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
            .alternateTypeRules(
                AlternateTypeRules.newRule(resolver.resolve(ObjectNode::class.java), resolver.resolve(Object::class.java))
            )
            .alternateTypeRules(RecursiveAlternateTypeRule(resolver, listOf(
                AlternateTypeRules.newRule(
                    resolver.resolve(Mono::class.java, WildcardType::class.java),
                    resolver.resolve(WildcardType::class.java)),
                AlternateTypeRules.newRule(
                    resolver.resolve(ResponseEntity::class.java, WildcardType::class.java),
                    resolver.resolve(WildcardType::class.java))
            )))
            .alternateTypeRules(RecursiveAlternateTypeRule(resolver, listOf(
                AlternateTypeRules.newRule(
                    resolver.resolve(Flux::class.java, WildcardType::class.java),
                    resolver.resolve(List::class.java, WildcardType::class.java)),
                AlternateTypeRules.newRule(
                    resolver.resolve(ResponseEntity::class.java, WildcardType::class.java),
                    resolver.resolve(WildcardType::class.java))
            )))
    }
}
