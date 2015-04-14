Recurly Grails Plugin
=====================

# Introduction

The **Recurly Plugin** allows you to integrate [Recurly](https://recurly.com) subscription billing in your [Grails](http://grails.org) application.

Recurly offers enterprise-class subscription billing and recurring billing management for thousands of companies worldwide.

**Recurly Plugin** provides the following Grails artefacts:
* **RecurlyService** - A service client to call [Recurly APIs v2](https://docs.recurly.com/api).
* **RecurlyAccount**, **RecurlyPlan**, **RecurlySubscription**, etc - REST resource clients to call [Recurly APIs v2](https://docs.recurly.com/api).
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
				runtime ':recurly:2.2.6'
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
            apiKey = {RECURLY_PRIVATE_API_KEY} // To communicate with Recurly's API v2
            publicKey = {RECURLY_PUBLIC_KEY}   // To identify your site when using Recurly.js v3.
            webhook {
                user = "user" // Optional, for push notifications authentication
                pass = "pass" // Optional, for push notifications authentication
                repostUrl = {REPOST_URL} // Optional, if defined, every push notification received will be resent (useful to use as service like ChartMogul)
            }
        }
    }
}
```

# Usage

## Recurly APIs

To use Recurly APIs, you can use _recurlyService_ (easier to mock in tests) or _Recurly*_ REST resource clients (easier to use).


### RecurlyService

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


### Recurly REST resource clients

Or you can use a friendlier static methods added to domain/REST objects in order to call [Recurly APIs](https://docs.recurly.com/api).

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
For more details, check:

- [RecurlyAccount Groovy docs](http://agorapulse.github.io/grails-recurly/gapi/grails/plugin/recurly/RecurlyAccount.html)
- [RecurlyBillingInfo Groovy docs](http://agorapulse.github.io/grails-recurly/gapi/grails/plugin/recurly/RecurlyBillingInfo.html)
- [RecurlyCreditCard Groovy docs](http://agorapulse.github.io/grails-recurly/gapi/grails/plugin/recurly/RecurlyCreditCard.html)
- [RecurlyInvoice Groovy docs](http://agorapulse.github.io/grails-recurly/gapi/grails/plugin/recurly/RecurlyInvoice.html)
- [RecurlyPlan Groovy docs](http://agorapulse.github.io/grails-recurly/gapi/grails/plugin/recurly/RecurlyPlan.html)
- [RecurlySubscription Groovy docs](http://agorapulse.github.io/grails-recurly/gapi/grails/plugin/recurly/RecurlySubscription.html)
- [RecurlyTransaction Groovy docs](http://agorapulse.github.io/grails-recurly/gapi/grails/plugin/recurly/RecurlyTransaction.html)


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

Note: if required, you can also repost received push notification to another webhook (useful to use as service like ChartMogul), by setting `grails.plugin.recurly.webhook.repostUrl`.

### Webhook service example

```groovy
import grails.plugin.recurly.interfaces.RecurlyWebHookListener

class RecurlyWebHookService implements RecurlyWebHookListener {

    static transactional = true

    // Account notifications

    void newAccountNotificationHandler(RecurlyNewAccountWebHookNotification notification) {
        log.debug "Processing new account notification..."
        //Handler code here
    }

    void canceledAccountNotificationHandler(RecurlyCanceledAccountWebHookNotification notification) {
        log.debug "Processing canceled account notification..."
        //Handler code here
    }

    void billingInfoUpdatedNotificationHandler(RecurlyBillingInfoUpdatedWebHookNotification notification) {
        log.debug "Processing billing info updated notification..."
        //Handler code here
    }

    void reactivatedAccountNotificationHandler(RecurlyReactivatedAccountWebHookNotification notification) {
        log.debug "Processing reactivated account notification..."
        //Handler code here
    }

    // Payment notifications

    void successfulPaymentNotificationHandler(RecurlySuccessfulPaymentWebHookNotification notification) {
        log.debug "Processing successful payment notification..."
        //Handler code here
    }

    void successfulRefundNotificationHandler(RecurlySuccessfulRefundWebHookNotification notification) {
        log.debug "Processing successful refund notification..."
        //Handler code here
    }

    void failedPaymentNotificationHandler(RecurlyFailedPaymentWebHookNotification notification) {
        log.debug "Processing failed payment notification..."
        //Handler code here
    }

    void voidPaymentNotificationHandler(RecurlyVoidPaymentWebHookNotification notification) {
        log.debug "Processing void payment notification..."
        //Handler code here
    }

    // Subscription notifications

    void newSubscriptionNotificationHandler(RecurlyNewSubscriptionWebHookNotification notification) {
        log.debug "Processing new subscription notification..."
        //Handler code here
    }

    void cancelledSubscriptionNotificationHandler(RecurlyCanceledSubscriptionWebHookNotification notification) {
        log.debug "Processing cancelled subscription notification..."
        // Handler Code Here
    }

    void expiredSubscriptionNotificationHandler(RecurlyExpiredSubscriptionWebHookNotification notification) {
        log.debug "Processing expired subscription notification..."
        //Handler code here
    }

    void renewedSubscriptionNotificationHandler(RecurlyRenewedSubscriptionWebHookNotification notification) {
        log.debug "Processing renewed subscription notification..."
        //Handler code here
    }

    void updatedSubscriptionNotificationHandler(RecurlyUpdatedSubscriptionWebHookNotification notification) {
        log.debug "Processing updated subscription notification..."
        //Handler code here
    }

}
```


# Latest releases

* 2015-04-03 **V2.2.5** : Fix in Subscription processor to handle add-ons correctly
* 2015-02-26 **V2.2.4** : Fix in BillingInfo processor to handle update for subscriptions made through Paypal
* 2015-02-16 **V2.2.3** : Recurly invoice methods fixed (invoice number prefix handled)
* 2015-01-21 **V2.2.2** : minor fix
* 2015-01-21 **V2.2.1** : fix in billing info processor to add vat_number, and in subscription processor to enabled account company name update
* 2015-01-16 **V2.2.0** : upgrade to Recurly.js v3 + bug fixes in subscriptions processor.

BREAKING: Since V2.2.0, Recurly.js v2 has been deprecated in favor of v3.
So legacy `privateApiKey` has been removed from config and there is a new `publicKey` to identify your site when using Recurly.js v3.

* 2014-11-24 **V2.1.3** : Grails 2.4 compatibility
* 2014-05-19 **V2.1.2** : fix failed payment notification web hook bug
* 2014-05-19 **V2.1.1** : fix new account notification web hook bug
* 2014-05-14 **V2.1.0** : updated notification handlers (WARNING: breaking change! Please update your `RecurlyWebHookService`)
  - new handlers `newAccount`, `canceledAccount`, `billingInfoUpdated`, `successfulRefund`, `voidPayment`
  - updated handlers `subscriptionUpdated` renamed to `updatedSubscription` and `failedRenewalPayment` renamed to `failedPayment` (to match recurly naming)
* 2014-03-13 **V2.0.1** : release on grails.org
* 2013-11-09 **V2.0.0** : plugin refactored to support [Recurly APIs v2](https://docs.recurly.com/api).
* 2011-09-19 **V0.99** : initial release by [Kushal Likhi](https://github.com/kushal-likhi), based on [Recurly APIs v1](http://docs.recurly.com/api/v1) (deprecated)

# Bugs

To report any bug, please use the project [Issues](http://github.com/agorapulse/grails-recurly/issues) section on GitHub.