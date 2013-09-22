import org.springframework.context.ApplicationContext
import org.codehaus.groovy.grails.commons.spring.GrailsApplicationContext
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.devunited.grails.plugin.recurly.RecurlyWebHookController

class RecurlyGrailsPlugin {
    // the plugin version
    def version = "0.99"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.5 > *"
    // the other plugins this plugin depends on
    def dependsOn = ["rest": "0.6.1 > *"]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp",
            "grails-app/controllers/recurlyplugin/UtilController.groovy"
    ]

    def author = "Kushal Likhi"
    def authorEmail = "kushal.likhi@gmail.com"
    def title = "Recurly Plugin"
    def description = '''\\
Recurly Grails API.
'''

    // URL to the plugin's documentation
    def documentation = "http://www.devunited.org/recurly-plugin/grails-recurly-plugin-usage-guide/"

    def doWithWebDescriptor = { xml ->
    }

    def doWithSpring = {
    }

    def doWithDynamicMethods = { ctx ->
    }

    def doWithApplicationContext = { applicationContext ->
        application.serviceClasses.each {service ->
            if (service.getStaticPropertyValue("recurlyWebHook", Boolean.class)) {
                RecurlyWebHookController.handlerBean = applicationContext.getBean(service.getName().replaceFirst(/^\w/, service.getName().charAt(0).toLowerCase().toString()) + "Service")
            }
        }
        ConfigurationHolder.config.recurly.userName = "api@${ConfigurationHolder.config.recurly.subDomain}.com"
        ConfigurationHolder.config.recurly.password = ConfigurationHolder.config.recurly.apiKey
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
        ConfigurationHolder.config.recurly.userName = "api@${ConfigurationHolder.config.recurly.subDomain}.com"
        ConfigurationHolder.config.recurly.password = ConfigurationHolder.config.recurly.apiKey
    }
}