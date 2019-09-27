package com.kongqw.kbox.common

import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

object FileUtils {

    /**
     * 写文件
     *
     * @param filePath 文件绝对路径
     * @param content  写入内容
     * @return 写入是否成功
     */
    @JvmStatic
    fun writeFile(filePath: String, content: String): Boolean {
        var fileOutputStream: FileOutputStream? = null
        var bufferedOutputStream: BufferedOutputStream? = null
        try {
            val file = File(filePath)
            var fileExists = true
            if(!file.exists() && !file.isFile){
                fileExists = file.createNewFile()
            }
            return if (fileExists) {
                fileOutputStream = FileOutputStream(filePath)
                bufferedOutputStream = BufferedOutputStream(fileOutputStream)
                bufferedOutputStream.write(content.toByteArray(charset("UTF-8")))
                bufferedOutputStream.flush()
                true
            } else {
                false
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                fileOutputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                bufferedOutputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return false
    }

    /**
     * 读文件
     *
     * @param filePath 文件路径
     * @return 文件内容
     */
    @JvmStatic
    fun readFile(filePath: String): String {

        val stringBuffer = StringBuilder()

        val file = File(filePath)

        if (file.exists()) {
            var fileInputStream: FileInputStream? = null
            var inputStreamReader: InputStreamReader? = null
            var bufferedReader: BufferedReader? = null
            try {
                fileInputStream = FileInputStream(file)
                inputStreamReader = InputStreamReader(fileInputStream, "UTF-8")
                bufferedReader = BufferedReader(inputStreamReader)

                do {
                    val mimeTypeLine = bufferedReader.readLine()
                    if (null != mimeTypeLine) {
                        stringBuffer.append(mimeTypeLine)
                    }
                } while (null != mimeTypeLine)

            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    fileInputStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                try {
                    inputStreamReader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                try {
                    bufferedReader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        return stringBuffer.toString()
    }
}
