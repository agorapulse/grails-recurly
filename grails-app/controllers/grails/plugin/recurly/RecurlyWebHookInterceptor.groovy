package grails.plugin.recurly

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class RecurlyWebHookInterceptor {

    static recurlyWebHookService

    boolean before() {
        log.debug "Before $controllerName $actionName ($request.method) interceptor..."
        if (!recurlyWebHookService) {
            log.warn 'No webhook handler defined in the application (RecurlyWebHookService must be created in your app and implement RecurlyWebHookListener)'
            response.status = 500
            render 'Handler Not Defined For the Application'
            return false
        }
        true
    }

}
