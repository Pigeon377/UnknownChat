package com.thyme.extension

object ExtensionFunction {

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
