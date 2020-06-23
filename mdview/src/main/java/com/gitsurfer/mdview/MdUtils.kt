package com.gitsurfer.mdview

import android.util.Base64
import android.util.Log
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.regex.Pattern

object MdUtils {
  private const val IMAGE_PATTERN = "!\\[(.*)\\]\\((.*)\\)"

  @JvmStatic
  fun imgToBase64(mdText: String): String? {
    val ptn = Pattern.compile(IMAGE_PATTERN)
    val matcher = ptn.matcher(mdText)
    if (!matcher.find()) {
      return mdText
    }
    val imgPath = matcher.group(2)

    imgPath?.let {
      if (isUrlPrefix(imgPath) || !isPathExCheck(imgPath)) {
        return mdText
      }
      val baseType: String? = imgEx2BaseType(imgPath)
      if ("" == baseType) {
        return mdText
      }
      val file = File(imgPath)
      val bytes = ByteArray(file.length().toInt())
      try {
        val buf =
          BufferedInputStream(FileInputStream(file))
        buf.read(bytes, 0, bytes.size)
        buf.close()
      } catch (e: FileNotFoundException) {
        Log.e(MdView.TAG, "FileNotFoundException:$e")
      } catch (e: IOException) {
        Log.e(MdView.TAG, "IOException:$e")
      }
      val base64Img =
        baseType + Base64.encodeToString(bytes, Base64.NO_WRAP)
      return mdText.replace(imgPath, base64Img)
    }
    return null
  }

  @JvmStatic
  private fun isUrlPrefix(text: String): Boolean {
    return text.startsWith("http://") || text.startsWith("https://")
  }

  @JvmStatic
  private fun isPathExCheck(text: String): Boolean {
    return (text.endsWith(".png")
        || text.endsWith(".jpg")
        || text.endsWith(".jpeg")
        || text.endsWith(".gif"))
  }

  @JvmStatic
  private fun imgEx2BaseType(text: String): String? {
    return if (text.endsWith(".png")) {
      "data:image/png;base64,"
    } else if (text.endsWith(".jpg") || text.endsWith(".jpeg")) {
      "data:image/jpg;base64,"
    } else if (text.endsWith(".gif")) {
      "data:image/gif;base64,"
    } else {
      ""
    }
  }

  @JvmStatic
  fun escapeForText(mdText: String?): String {
    var escText = mdText!!.replace("\n", "\\\\n")
    escText = escText.replace("'", "\\\'")
    escText = escText.replace("\r", "")
    return escText
  }
}