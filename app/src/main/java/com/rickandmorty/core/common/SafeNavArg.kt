package com.rickandmorty.core.common

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * This function is used to avoid navigation error caused by url arguments.
 * @param string String to be encoded.
 * @return the encoded String.
 */
fun encodeForSafe(string: String): String =
    URLEncoder.encode(string, StandardCharsets.UTF_8.toString())