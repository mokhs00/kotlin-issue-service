package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinIssueServiceApplication

fun main(args: Array<String>) {
	runApplication<KotlinIssueServiceApplication>(*args)
}
