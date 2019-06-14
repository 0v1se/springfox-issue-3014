package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@SpringBootApplication
@Import(SwaggerConfiguration::class)
class DemoApplication

@RestController
class TestController {
	@GetMapping("/test")
	fun test(): SimpleDataClass {
		return SimpleDataClass("name", "value")
	}

	@PostMapping("/test")
	fun testPost(@RequestBody form: SimpleDataClass): Mono<Void> {
		return Mono.empty()
	}
}

data class SimpleDataClass(
	val name: String,
	val value: String
)

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}
