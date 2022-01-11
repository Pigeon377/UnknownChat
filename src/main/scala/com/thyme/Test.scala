package com.thyme

import akka.util.Timeout

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

object Test {

    implicit val timeout: Timeout = 10 seconds

    def start(): Unit = {
    }
}


