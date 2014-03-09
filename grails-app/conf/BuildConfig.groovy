grails.project.work.dir = 'target'
grails.project.source.level = 1.6

grails.project.dependency.resolver = 'maven' // or ivy
grails {
    project {
        dependency {
            distribution = {
                remoteRepository(id: "internal", url: "file://${basedir}/mvn-repo")
            }
            resolution = {
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
                    build ':release:3.0.1', ':rest-client-builder:1.0.3', {
                        export = false
                    }

                    compile ':rest:0.8'
                }

            }

        }

    }
}