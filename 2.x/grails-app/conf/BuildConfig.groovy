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
                    // Latest httpcore and httpmime for Coveralls plugin
                    build 'org.apache.httpcomponents:httpcore:4.3.2'
                    build 'org.apache.httpcomponents:httpclient:4.3.2'
                    build 'org.apache.httpcomponents:httpmime:4.3.3'
                }
                plugins {
                    build(':release:3.0.1',
                            ':rest-client-builder:2.1.1',
                            ':coveralls:0.1.3') {
                        export = false
                    }
                    test(':code-coverage:2.0.3-3') {
                        export = false
                    }
                    compile ':rest:0.8'
                }

            }

        }

    }
}