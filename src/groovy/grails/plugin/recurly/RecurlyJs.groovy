package grails.plugin.recurly

import grails.plugin.recurly.helpers.RecurlyRESTResource
import grails.plugin.recurly.processors.RecurlyJsProcessor
import grails.util.Holders

import javax.crypto.Mac
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class RecurlyJs extends RecurlyRESTResource {

    static config = Holders.config.grails?.plugin?.recurly

    static def fetchResult(String recurlyToken) {
        handleResponse(new RecurlyJsProcessor().fetch(recurlyToken))
    }

    static String sign(Map data) {
        String digest = digest(data)
        String signedDigest = calculateHMACSha1(digest, config.privateApiKey.toString())
        return "${signedDigest}|${digest}"
    }

    // PRIVATE

    private static String calculateHMACSha1(String data,
                                            String key) {
        assert key
        SecretKey secretKey = new SecretKeySpec(key.getBytes('ISO-8859-1'), "HmacSHA1")
        Mac m = Mac.getInstance("HmacSHA1")
        m.init(secretKey)
        byte[] mac = m.doFinal(data.getBytes())
        StringBuilder sb = new StringBuilder(mac.length * 2)
        Formatter formatter = new Formatter(sb)
        for (byte b : mac) {
            formatter.format("%02x", b)
        }
        return sb.toString().toLowerCase()
    }

    private static String digest(Map data) {
        if (!data.nonce) data.nonce = UUID.randomUUID().toString().replaceAll('-', 'a')
        if (!data.timestamp) data.timestamp = (System.currentTimeMillis() / 1000).toInteger()

        Map sortedData = data.sort { a, b -> a.key <=> b.key }
        sortedData.collect { item ->
            if (item.value instanceof String || item.value instanceof Integer) {
                "${item.key}=${item.value}"
            } else if (!(item.value instanceof Integer)) {
                Map itemValueSorted = item.value.sort { a1, b1 -> a1.key <=> b1.key }
                itemValueSorted.collect { item1 ->
                    "${item.key}%5B${item1.key}%5D=${item1.value}"
                }
            }
        }.flatten().join('&')
    }

}
