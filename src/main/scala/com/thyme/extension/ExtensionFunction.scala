package com.thyme.extension

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

object ExtensionFunction {

    private val jwtSecretKey: String = "扎不多得勒"
    private val jwtIssuer: String = "Pigeon377"
    private val jwtSubject: String = "token"
    private val jwtEffectiveTime: Long = 7 * 24 * 60 * 60 * 1000

    def generatePasswordHash(password: String): Unit = {

    }

    /**
     * @param uuid is audience's uuid
     * */
    def generateJwtToken(uuid: Long): String = {
        JWT.create()
            .withSubject(jwtSubject)
            .withIssuer(jwtIssuer)
            .withAudience(uuid.toString)
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
    def checkJwtToken(tokenString: String, uuid: Long): Boolean = {
        try {
            val decodeJwt = JWT.require(Algorithm.HMAC512(jwtSecretKey)).build().verify(tokenString)
            val decodeIssuer = decodeJwt.getIssuer
            val decodeSubject = decodeJwt.getSubject
            val decodeUUID = decodeJwt.getAudience.get(0)
            decodeIssuer == jwtIssuer && decodeSubject == jwtSubject && uuid.toString == decodeUUID
        } catch {
            case _: Exception => false
        }
    }


    /**
     * the result will depend on your uuid
     * and never change!
     * so ,you should change this to make it connect with time
     * although this operator will make function not be a pure function again
     * */
    def generateCheckNumberWithUUID(uuid: Long): Long = {
        Math.abs(
            (uuid.toString.hashCode & 0x7FFFFFFF +
                (uuid * ("1145141919810".hashCode - 7 + "Pigeon3777777".hashCode)
                    - (uuid + "sdcardasdagvcbdshhdst_dehrbsdfbbuggers".hashCode)) * 0x5DEECE66DL + 0xBL)
                & 0xFFFFFFFFFFFFFL)
    }

    /**
     * @param uuid        Long
     * @param checkNumber Long
     *                    as you see, judge uuid's tokenNumber is equals to checkNumber are equal or not
     *
     * */
    def checkTokenNumberWithUUID(uuid: Long, checkNumber: Long): Boolean = {
        generateCheckNumberWithUUID(uuid) == checkNumber
    }

}
