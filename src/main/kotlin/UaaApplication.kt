package com.machinemode.uaa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UaaApplication

fun main(args: Array<String>) {
    runApplication<UaaApplication>(*args)
}
