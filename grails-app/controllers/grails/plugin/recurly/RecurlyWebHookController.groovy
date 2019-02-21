package grails.plugin.recurly

import com.ning.billing.recurly.model.push.Notification
import grails.core.GrailsApplication
import groovy.util.logging.Slf4j

import static com.ning.billing.recurly.model.push.Notification.detect
import static com.ning.billing.recurly.model.push.Notification.read

@Slf4j
class RecurlyWebHookController {

    static defaultAction = 'acceptNotice'

    GrailsApplication grailsApplication
    RecurlyPushNotificationService recurlyPushNotificationService

    def acceptNotice() {
        log.debug 'Accepting notice...'

        if (config?.webhook?.user) {
            def authString = request.getHeader('Authorization')
            if (!authString){
                response.status = 401
                render 'Authentication Failure'
                return false
            }

            def encodedPair = authString - 'Basic '
            def decodedPair =  new String(encodedPair.decodeBase64());
            def credentials = decodedPair.tokenize(':')
            if (!credentials || credentials.first() != config.webhook.user || credentials.last() != config.webhook.pass) {
                response.status = 401
                render 'Authentication Failure'
                return false
            }
        }

        String notificationXml
        try {
            StringBuffer stringBuffer = new StringBuffer()
            BufferedReader bufferedReader = request.getReader()
            bufferedReader.eachLine {
                stringBuffer.append(it)
            }
            notificationXml = stringBuffer.toString()
        } catch (Exception ignored) {
            response.status = 500
            return
        }


        if (!notificationXml) {
            response.status = 400
            render 'Missing Payload'
            return false
        }

        recurlyPushNotificationService.dispatchNotification(read(notificationXml, detect(notificationXml).javaType) as Notification)

        response.status = 201
        render 'Data parsed and accepted'
    }

    // PRIVATE

    def getConfig() {
        grailsApplication.config.grails?.plugin?.recurly
    }

}
