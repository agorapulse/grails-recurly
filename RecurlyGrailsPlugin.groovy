import grails.plugin.recurly.RecurlyWebHookController

class RecurlyGrailsPlugin {
    // the plugin version
    def version = "2.0.0-SNAPSHOT"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.5 > *"
    // the other plugins this plugin depends on
    def dependsOn = ["rest": "0.6.1 > *"]

    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp",
            "web-app/css/**/*.*",
            "web-app/js/**/*.*",
            "docs/*.*"
    ]

    def author = "Kushal Likhi"
    def authorEmail = "kushal.likhi@gmail.com"
    def title = "Recurly Plugin"
    def description = '''\\
Recurly Grails API.
'''
    def license = "APACHE"
    def developers = [
            [ name: "Kushal Likhi", email: "kushal.likhi@gmail.com" ],
            [ name: "Benoit Hediard", email: "ben@benorama.com" ]
    ]
    def documentation = "https://github.com/agorapulse/grails-recurly"
    def issueManagement = [ system: "github", url: "https://github.com/agorapulse/grails-recurly/issues" ]
    def scm = [  url: "https://github.com/agorapulse/grails-recurly" ]

    def doWithApplicationContext = { applicationContext ->
        application.serviceClasses.each {service ->
            if (service.getStaticPropertyValue("recurlyWebHook", Boolean.class)) {
                String beanName = service.getName().replaceFirst(/^\w/, service.getName().charAt(0).toLowerCase().toString()) + "Service"
                RecurlyWebHookController.handlerBean = applicationContext.getBean(beanName)
            }
        }
    }

}