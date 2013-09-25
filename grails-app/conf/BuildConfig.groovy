grails.project.work.dir = 'target'
grails.project.source.level = 1.6

grails.project.dependency.resolution = {
    inherits 'global'
    log 'warn'
    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }
    dependencies {
    }
    plugins {
        build ":tomcat:$grailsVersion"
        build ':release:2.2.1', ':rest-client-builder:1.0.3', {
            export = false
        }

        compile ':rest:0.7'

        // To debug
        compile(':console:1.2') {
            export = false
        }
    }
}