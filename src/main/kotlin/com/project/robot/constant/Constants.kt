package com.project.robot.constant

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component(value = "constant")
class Constants {
    companion object {
        const val TOPIC = "ROBOT"
        const val SOCKET_END_POINT = "/robot"
        const val SOCKET_ORIGIN = "*"
        const val MESSAGE_END_POINT = "/message"
    }

}

