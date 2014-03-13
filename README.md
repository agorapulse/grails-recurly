Recurly Grails Plugin
=====================

# Introduction

The **Recurly Plugin** allows you to integrate [Recurly](https://recurly.com) subscription billing in your [Grails](http://grails.org) application.

Recurly offers enterprise-class subscription billing and recurring billing management for thousands of companies worldwide.

**Recurly Plugin** provides the following Grails artefacts:
* **RecurlyService** - A service client to call [Recurly APIs v2](https://docs.recurly.com/api).
* **RecurlyAccount**, **RecurlyPlan**, **RecurlySubscription**, etc - API/domain clients to call [Recurly APIs v2](https://docs.recurly.com/api).
* **RecurlyWebHook** -  Web hook end point for [Recurly Push Notifications](http://docs.recurly.com/api/push-notifications).

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

Create a [Recurly](https://recurly.com) account, in order to get your own credentials.

Add your _apiKey_  and _privateKey_ to your _grails-app/conf/Config.groovy_:

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
import grails.plugin.recurly.*

recurlyService = ctx.getBean('recurlyService')

// Account - GET
def existingAccount = recurlyService.getAccountDetails('1').entity

// Account - CREATE
def account = new RecurlyAccount(
  userName: '',
  firstName: 'Verena',
  lastName: 'Example',
  accountCode: '4',
  email: 'some@example.com',
  companyName: 'ACME'
)
account = recurlyService.createAccount(newAccount)

// Account - UPDATE
account.userName = 'verena'
account = recurlyService.updateAccount(account).entity

// Account - DELETE (it will close the account)
recurlyService.deleteAccount('4')

// Account - LIST
def accounts = recurlyService.listAccounts()

// Plan - LIST
def plans = recurlyService.listAllSubscriptionPlans().entity

// Billing info - GET
def billingInfo = recurlyService.getBillingDetails('1').entity

// Billing info - UPDATE
billingInfo.firstName = 'Benoit'
billingInfo = recurlyService.createOrUpdateBillingDetails(details, '1').entity

// Etc
```

For more details, check [RecurlyService Groovy docs](http://agorapulse.github.io/grails-recurly/gapi/grails/plugin/recurly/RecurlyService.html)

Or you can use a friendlier static methods added to domain objects.

```groovy
import grails.plugin.recurly.*

// Account - GET
def existingAccount= RecurlyAccount.fetch('1')

// Account - CREATE
def account = new RecurlyAccount(
  userName: '',
  firstName: 'Verena',
  lastName: 'Example',
  accountCode: '5',
  email: 'verena@example.com',
  companyName: 'ACME'
).save()

// Account - UPDATE
account.userName = 'verena'
account.save()

// Account - DELETE (it will close the account)
account.delete()

// Account - LIST
def accounts = RecurlyAccount.query(state: 'active', max: 10)

// Billing info - GET
def billingInfo = RecurlyBillingInfo.fetch('1')

// Billing info - UPDATE
billingInfo.firstName = 'Benoit'
billingInfo.save()

// Etc
```


## RecurlyWebHook (push notifications)

Recurly can send [push notifications](http://docs.recurly.com/push-notifications) to any publicly accessible server.
When an event in Recurly triggers a push notification (e.g., an account is opened), Recurly will attempt to send this notification to the endpoint you specify.

This Plugin has built-in mechanism to accept, parse and process the notification and call the desired handler in the application.

Implementing handler is a **VERY SIMPLE** process. All you have to do is:

1. Create a service, ex. _RecurlyWebHookService_

2. Implement the interface _RecurlyWebHookListener_ by adding all the interface methods (your IDE will do that for you :) )

3. In Recurly web app > Push Notifications > Configuration, enter your public push notification URL (`http://your.domain.com/recurlyWebHook`) and HTTP Auth credentials (if defined in your _grails-app/conf/Config.groovy_).

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

* 2014-03-13 **V2.0.1** : release on grails.org
* 2013-11-09 **V2.0.0** : plugin refactored to support [Recurly APIs v2](https://docs.recurly.com/api).
* 2011-09-19 **V0.99** : initial release by [Kushal Likhi](https://github.com/kushal-likhi), based on [Recurly APIs v1](http://docs.recurly.com/api/v1) (deprecated)

# Bugs

To report any bug, please use the project [Issues](http://github.com/agorapulse/grails-recurly/issues) section on GitHub.