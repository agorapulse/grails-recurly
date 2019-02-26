package grails.plugin.recurly

import com.ning.billing.recurly.RecurlyClient
import grails.plugins.Plugin
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

@Slf4j
@CompileStatic
class RecurlyGrailsPlugin extends Plugin {

    def grailsVersion = "3.3 > *"
    def author = "Kushal Likhi"
    def authorEmail = "kushal.likhi@gmail.com"
    def title = "Recurly Plugin"
    def description = 'Recurly Grails API.'
    def license = "APACHE"
    def developers = [
            [name: "Kushal Likhi", email: "kushal.likhi@gmail.com"],
            [name: "Benoit Hediard", email: "ben@benorama.com"],
            [name: "Florian Ernoult", email: "floernoult@gmail.com"],
            [name: "Vladimir Orany", email: "vladimir@orany.cz"]
    ]

    def documentation = "https://github.com/agorapulse/grails-recurly"
    def issueManagement = [system: "github", url: "https://github.com/agorapulse/grails-recurly/issues"]
    def scm = [url: "https://github.com/agorapulse/grails-recurly"]

    @Override
    @CompileDynamic
    Closure doWithSpring() {
        final String apiKey = config.grails.plugin.recurly.apiKey
        final String subDomain = config.grails.plugin.recurly.subDomain

        if (!apiKey || !subDomain) {
            log.error("Missing configuration for Recurly plugin! Please set grails.plugin.recurly.apiKey and grails.plugin.recurly.subDomain configuration properties")
            return { }
        }

        return { ->
            recurlyClient(RecurlyClient, apiKey, subDomain) { bean ->
                bean.initMethod = 'open'
                bean.destroyMethod = 'close'
            }
        }
    }
}
