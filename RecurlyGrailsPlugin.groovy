import grails.plugin.recurly.RecurlyWebHookController

class RecurlyGrailsPlugin {

    def version = "2.2.1"
    def grailsVersion = "2.0 > *"
    def dependsOn = ["rest": "0.6.1 > *"]

    def author = "Kushal Likhi"
    def authorEmail = "kushal.likhi@gmail.com"
    def title = "Recurly Plugin"
    def description = 'Recurly Grails API.'
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