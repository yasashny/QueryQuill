/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

package org.queryquill.app.core.network.utils

import java.net.URI

internal object CookieChecker {

    fun getRelevantCookies(url: String, cookies: List<String>): List<String> {
        val urlParts = parseUrl(url) ?: return emptyList()
        return cookies.filter { cookie ->
            val params = parseCookie(cookie)
            isCookieRelevant(params, urlParts)
        }
    }

    private data class UrlParts(
        val host: String, val path: String, val isHttps: Boolean
    )

    private data class CookieParams(
        val domain: String?, val path: String?, val isSecure: Boolean
    )

    private fun parseUrl(url: String): UrlParts? {
        return try {
            val uri = URI(url)
            val host = uri.host ?: return null
            val path = uri.path.takeIf { it.isNotEmpty() } ?: "/"
            UrlParts(
                host = host, path = path, isHttps = uri.scheme.equals("https", ignoreCase = true)
            )
        } catch (e: Exception) {
            null
        }
    }

    private fun parseCookie(cookie: String): CookieParams {
        var domain: String? = null
        var path: String? = null
        var isSecure = false

        cookie.split(';').forEach { part ->
            val trimmed = part.trim()
            when {
                trimmed.equals("Secure", ignoreCase = true) -> isSecure = true
                trimmed.startsWith("Domain=", ignoreCase = true) -> domain =
                    trimmed.substringAfter('=').trim().removePrefix(".")

                trimmed.startsWith("Path=", ignoreCase = true) -> path =
                    trimmed.substringAfter('=').trim()
            }
        }

        return CookieParams(domain, path, isSecure)
    }

    private fun isCookieRelevant(params: CookieParams, urlParts: UrlParts): Boolean {
        return isDomainMatch(params.domain, urlParts.host) && isPathMatch(
            params.path, urlParts.path
        ) && isSecureMatch(params.isSecure, urlParts.isHttps)
    }

    private fun isDomainMatch(cookieDomain: String?, urlHost: String): Boolean {
        if (cookieDomain == null) return false

        val normalizedCookieDomain = cookieDomain.lowercase()
        val normalizedUrlHost = urlHost.lowercase()

        return normalizedUrlHost == normalizedCookieDomain || normalizedUrlHost.endsWith(".${normalizedCookieDomain}")
    }

    private fun isPathMatch(cookiePath: String?, urlPath: String): Boolean {
        val effectiveCookiePath = cookiePath?.takeIf { it.isNotEmpty() } ?: "/"
        val normalizedCookiePath =
            if (effectiveCookiePath.endsWith("/")) effectiveCookiePath else "$effectiveCookiePath/"

        return urlPath.startsWith(normalizedCookiePath) || urlPath == effectiveCookiePath.removeSuffix(
            "/"
        )
    }

    private fun isSecureMatch(isCookieSecure: Boolean, isUrlHttps: Boolean): Boolean {
        return !isCookieSecure || isUrlHttps
    }
}