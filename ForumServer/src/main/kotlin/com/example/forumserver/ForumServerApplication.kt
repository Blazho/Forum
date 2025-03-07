package com.example.forumserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ForumServerApplication

fun main(args: Array<String>) {
	runApplication<ForumServerApplication>(*args)
}
