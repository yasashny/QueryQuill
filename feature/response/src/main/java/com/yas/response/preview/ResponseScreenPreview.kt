package com.yas.response.preview


//@Composable
//internal fun ResponseScreenPreview(
//    body: ImmutableByteArray, mimeType: String?, contentSubtype: String?
//) {
//
//    val contentType = mimeTypeToContentType("$mimeType/$contentSubtype")
//    when (contentType) {
//        ContentType.Text.HTML -> WebViewPage(html = body.byteArray.decodeToString())
//        ContentType.Image.JPEG -> Base64ImageDisplay(
//            base64String = Base64.getEncoder().encodeToString(body.byteArray),
//            utf8String = body.byteArray.decodeToString()
//        )
//
//        ContentType.Application.JSON -> ResponseScreenSource(
//            body.byteArray.decodeToString(), LanguageType.JSON
//        )
//
//        ContentType.Text.PLAIN -> ResponseScreenSource(
//            body.byteArray.decodeToString(), LanguageType.PLAIN
//        )
//
//        ContentType.Image.PNG -> Base64ImageDisplay(
//            base64String = Base64.getEncoder().encodeToString(body.byteArray),
//            utf8String = body.byteArray.decodeToString()
//        )
//
//        ContentType.Image.WEBP -> Base64ImageDisplay(
//            base64String = Base64.getEncoder().encodeToString(body.byteArray),
//            utf8String = body.byteArray.decodeToString()
//        )
//
//        ContentType.Text.XML -> ResponseScreenSource(
//            body.byteArray.decodeToString(), LanguageType.XML
//        )
//
//        null -> ResponseScreenSource(body.byteArray.decodeToString(), LanguageType.PLAIN)
//    }
//}