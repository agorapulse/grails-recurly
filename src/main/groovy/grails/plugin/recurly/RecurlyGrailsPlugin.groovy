package grails.plugin.recurly

import grails.plugins.Plugin

class RecurlyGrailsPlugin extends Plugin {

    def grailsVersion = "3.1 > *"
    def dependsOn = ["http-builder-helper": "1.0.2 > *"]

    def author = "Kushal Likhi"
    def authorEmail = "kushal.likhi@gmail.com"
    def title = "Recurly Plugin"
    def description = 'Recurly Grails API.'
    def license = "APACHE"
    def developers = [
            [ name: "Kushal Likhi", email: "kushal.likhi@gmail.com" ],
            [ name: "Benoit Hediard", email: "ben@benorama.com" ],
            [ name: "Vladimir Orany", email: "vladimir@orany.cz" ]
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
