Recurly Grails Plugin
=========================

# Introduction

The **Recurly Plugin** allows you to integrate [Recurly](https://recurly.com) subscription billing in your [Grails](http://grails.org) application.

Recurly offers enterprise-class subscription billing and recurring billing management for thousands of companies worldwide.

It provides the following Grails artefacts:
* **RecurlyService** - A service client to call [Recurly APIs v2](https://docs.recurly.com/api).
* **RecurlyWebHookListener** - WebHook end point to support [Recurly Push Notifications](http://docs.recurly.com/api/push-notifications).

# Installation

Declare the plugin dependency in the BuildConfig.groovvy file, as shown here:

```groovy
grails.project.dependency.resolution = {
		inherits("global") { }
		log "info"
		repositories {
				//your repositories
		}
		dependencies {
				//your dependencies
		}
		plugins {
				//here go your plugin dependencies
				runtime ':recurly:2.0.0'
		}
}
```


# Config

Create a [Recurly](https://recurly.com) account, in order to get your own _apiKey_ and _privateKey_.

Add your Segment.io site _apiKey_  and _privateKey_ to your _grails-app/conf/Config.groovy_:

```groovy
grails {
    plugin {
        recurly {
            subDomain = "yourSubDomainHere"
            apiKey = {RECURLY_API_KEY}
            privateApiKey = {RECURLY_SECRET_KEY}
            webhook {
                user = "user" // Optional, for push notifications authentication
                pass = "pass" // Optional, for push notifications authentication
            }
        }
    }
}
```

# Usage

## RecurlyService (APIs)

You can inject _recurlyService_ in any of your Grails artefacts (controllers, services...) in order to call [Recurly APIs](https://docs.recurly.com/api).

```groovy
def recurlyService

// Get all account
def accounts = recurlyService.listAccounts()

// TODO give more usage examples
```

## RecurlyWebHook (push notifications)

Recurly can send [push notifications](http://docs.recurly.com/push-notifications) to any publicly accessible server.
When an event in Recurly triggers a push notification (e.g., an account is opened), Recurly will attempt to send this notification to the endpoint you specify.

This Plugin has built-in mechanism to accept, parse and process the notification and call the desired handler in the application.

Implementing handler is a **VERY SIMPLE** Process. All You have to do is:

1. Create a service, ex. _RecurlyWebHookService_

2. Implement the interface _RecurlyWebHookListener_ by adding all the interface methods(Your IDE Will Do that For you :) )

3. In Recurly web app > Push Notifications > configuration, enter your public push notification URL (`http://your.domain.com/recurlyWebHook`) and web hook HTTP Auth credentials (if you have defined them in your _grails-app/conf/Config.groovy_).

Hurray... Now this service will be automatically registered as the handler for webHookEvents.
And the implemented methods will act as the handler to a particular event.

The name of the methods are self explanatory to tell what event they will handle.


### Webhook service example

```groovy
import grails.plugin.recurly.interfaces.RecurlyWebHookListener

class RecurlyWebHookService implements RecurlyWebHookListener {

    static transactional = true

    void cancelledSubscriptionNotificationHandler(RecurlyCanceledSubscriptionWebHookNotification notification) {
        log.debug "Processing cancelled subscription notification..."
        // Handler Code Here
    }

    void expiredSubscriptionNotificationHandler(RecurlyExpiredSubscriptionWebHookNotification notification) {
        log.debug "Processing expired subscription notification..."
        //Handler code here
    }

    void failedRenewalPaymentNotificationHandler(RecurlyFailedRenewalWebHookNotification notification) {
        log.debug "Processing failed renewal notification..."
        //Handler code here
    }

    void newSubscriptionNotificationHandler(RecurlyNewSubscriptionWebHookNotification notification) {
        log.debug "Processing new subscription notification..."
        //Handler code here
    }

    void reactivatedAccountNotificationHandler(RecurlyReactivatedAccountWebHookNotification notification) {
        log.debug "Processing reactivated account notification..."
        //Handler code here
    }

    void renewedSubscriptionNotificationHandler(RecurlyRenewedSubscriptionWebHookNotification notification) {
        log.debug "Processing renewed subscription notification..."
        //Handler code here
    }

    void subscriptionUpdatedNotificationHandler(RecurlyChangedSubscriptionWebHookNotification notification) {
        log.debug "Processing subscription update notification..."
        //Handler code here
    }

    void successfulPaymentNotificationHandler(RecurlySuccessfulPaymentWebHookNotification notification) {
        log.debug "Processing successful payment notification..."
        //Handler code here
    }

}
```


# Latest releases

* 2014-03-10 **V2.0.0** : plugin refactored to support [Recurly APIs v2](https://docs.recurly.com/api).


# Bugs

To report any bug, please use the project [Issues](http://github.com/agorapulse/grails-recurly/issues) section on GitHub.