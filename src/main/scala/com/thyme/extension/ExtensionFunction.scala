package com.thyme.extension

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

import java.security.MessageDigest
import scala.annotation.tailrec
import scala.util.Random

object ExtensionFunction {

    /**
     * identify user only with mailbox
     * */
    private val jwtSecretKey: String = "扎不多得勒"
    private val jwtIssuer: String = "Pigeon377"
    private val jwtSubject: String = "token"
    private val jwtEffectiveTime: Long = 7 * 24 * 60 * 60 * 1000

    private val randomPasswordSaltArray = "012346789.*-/+_=-/';.,abcdefghijklmnopqrstuvwxyz"

    @tailrec
    private def generatePasswordHashWithRecursion(salt: String, passwordString: String, deep: Int): String = {
        {
            deep match {
                case 0 => MessageDigest.getInstance("SHA-256").digest((salt + passwordString).getBytes).map(_.toChar).mkString
                case _ => generatePasswordHashWithRecursion(salt,
                    MessageDigest.getInstance("SHA-256").digest((salt + passwordString).getBytes).map(_.toChar).mkString,
                    deep - 1)
            }
        }
    }

    def generatePasswordHash(password: String, salt: String =
    (for (_ <- 0 to 7) yield randomPasswordSaltArray(Random.nextInt(randomPasswordSaltArray.length))).mkString): String = {
        salt + "$" + generatePasswordHashWithRecursion(salt, password, 27)
        // use hash to secret password
    }

    def checkPasswordHash(password: String, truePassword: String): Boolean = {
        generatePasswordHash(password, truePassword.split("\\$")(0)).equals(truePassword)
    }

    /**
     * @param mailbox is audience's mailbox
     * */
    def generateJwtToken(userId: Long): String = {
        JWT.create()
            .withSubject(jwtSubject)
            .withIssuer(jwtIssuer)
            .withAudience(userId.toString)
            .withIssuedAt(new java.util.Date())
            .withExpiresAt(new java.util.Date(System.currentTimeMillis() + jwtEffectiveTime))
            .sign(Algorithm.HMAC512(jwtSecretKey))
    }

    /**
     * check jwt token is legal or not
     *
     * @return
     * legal => true
     * illegal => false
     * */
    def checkJwtToken(tokenString: String,userId:Long): Boolean = {
        try {
            val decodeJwt = JWT.require(Algorithm.HMAC512(jwtSecretKey)).build().verify(tokenString)
            val decodeIssuer = decodeJwt.getIssuer
            val decodeSubject = decodeJwt.getSubject
            val decodeUserId = decodeJwt.getAudience.get(0).toLong
            decodeIssuer == jwtIssuer && decodeSubject == jwtSubject && userId == decodeUserId
        } catch {
            case _: Exception => false
        }
    }


    /**
     * the result will depend on your mailbox
     * and never change!
     * so ,you should change this to make it connect with time
     * although this operator will make function not be a pure function again
     * */
    def generateCheckNumberWithMailbox(userId: Long): Long = {
        Math.abs(
            (userId & 0x7FFFFFFF +
                (userId * ("1145141919810".hashCode - 7 + "Pigeon3777777".hashCode)
                    - (userId + "sdcardasdagvcbdshhdst_dehrbsdfbbuggers".hashCode)) * 0x5DEECE66DL + 0xBL)
                & 0xFFFFFFFFFFFFFL)
    }

    /**
     * @param userId     String
     * @param checkNumber Long
     *                    as you see, judge  mailbox's tokenNumber is equals to checkNumber are equal or not
     *
     * */
    def checkTokenNumberWithMailbox(userId: Long, checkNumber: Long): Boolean = {
        generateCheckNumberWithMailbox(userId) == checkNumber
    }

}
