package com.thyme.actor.database

import org.mongodb.scala.Observable

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

object DatabaseExtension {
    trait ImplicitObservable[T]{
        val observable:Observable[T]
        def result(): Seq[T] = Await.result(observable.toFuture(),3 seconds)

    }

}
