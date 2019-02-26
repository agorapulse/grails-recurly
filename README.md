Recurly Grails Plugin
=====================

# Introduction

The **Recurly Plugin** allows you to integrate [Recurly](https://recurly.com) subscription billing in your [Grails](http://grails.org) application.

Recurly offers enterprise-class subscription billing and recurring billing management for thousands of companies worldwide.

**Recurly Plugin** provides the following Grails artefacts:
* **RecurlyClient** - Injectable instance of [the official Java client](https://github.com/killbilling/recurly-java-library)
* **RecurlyWebHook** -  Web hook end point for [Recurly Push Notifications](http://docs.recurly.com/api/push-notifications).

# Installation

Declare the plugin dependency in the _build.gradle_ file, as shown here:

```groovy
repositories {
    ...
    maven { url "http://dl.bintray.com/agorapulse/plugins" }
    ...
}

dependencies {
    ...
    compile "org.grails.plugins:recurly:4.0.1"
}
```


# Config

Create a [Recurly](https://recurly.com) account, in order to get your own credentials.

Add your `apiKey`  and `privateKey` to your `grails-app/conf/application.yml`:

```groovy
grails:
    plugin:
        recurly:
            subDomain: yourSubDomainHere    // Your subdomain
            apiKey: RECURLY_PRIVATE_API_KEY // To communicate with Recurly's API v2
            webhook:
                user: username // Optional, for push notifications authentication
                pass: password // Optional, for push notifications authentication
            }
        }
    }
}
```

# Usage

## Recurly APIs

To use Recurly APIs, you can use _recurlyClient_ which is populated with your credentials from the configuration file.


### RecurlyClient

You can inject _recurlyClient_ in any of your Grails artefacts (controllers, services...) in order to call [Recurly APIs](https://docs.recurly.com/api).

```groovy
import com.ning.billing.recurly.*
import com.ning.billing.recurly.model.*

RecurlyClient recurlyClient = ctx.getBean('recurlyClient')

// Account - GET
def existingAccount = recurlyClient.getAccountDetails('1').entity

// Account - CREATE
Account account = new Account(
  userName: '',
  firstName: 'Verena',
  lastName: 'Example',
  accountCode: '4',
  email: 'some@example.com',
  companyName: 'ACME'
)
account = recurlyClient.createAccount(account)

// Account - UPDATE
account.userName = 'verena'
account = recurlyClient.updateAccount(account).entity

// Account - DELETE (it will close the account)
recurlyClient.deleteAccount('4')

// Account - LIST
def accounts = recurlyClient.listAccounts()

// Plan - LIST
def plans = recurlyClient.listAllSubscriptionPlans().entity

// Billing info - GET
def billingInfo = recurlyClient.getBillingDetails('1').entity

// Billing info - UPDATE
billingInfo.firstName = 'Benoit'
billingInfo = recurlyClient.createOrUpdateBillingDetails(details, '1').entity

// Etc
```

For more details, check [RecurlyClient Tests](https://github.com/killbilling/recurly-java-library/blob/master/src/test/java/com/ning/billing/recurly/TestRecurlyClient.java)

## RecurlyWebHook (push notifications)

Recurly can send [push notifications](http://docs.recurly.com/push-notifications) to any publicly accessible server.
When an event in Recurly triggers a push notification (e.g., an account is opened), Recurly will attempt to send this notification to the endpoint you specify.

This Plugin has built-in mechanism to accept, parse and process the notification and call the desired handler in the application. All the incoming notifications
are published as events e.g. `successfulPaymentNotification`. See [Grails Async](http://async.grails.org/) for more information.

Implementing handler is a **VERY SIMPLE** process. All you have to do is:
1. Create a service with subscriber (alternatively you can extend `AbstractRecurlyWebHookService`) 
2. In Recurly web app > Push Notifications > Configuration, enter your public push notification URL (`http://your.domain.com/recurlyWebHook`) and optinal HTTP Auth credentials (if defined in your `grails-app/conf/application.yml`).

Hurray... Now this service will be automatically registered as the handler for webHookEvents.
And the implemented methods will act as the handler to a particular event.

The name of the methods are self explanatory to tell what event they will handle.

### Webhook service example

```groovy
import com.ning.billing.recurly.model.push.payment.SuccessfulPaymentNotification
import grails.events.annotation.Subscriber

class RecurlyPaymentsService {

    @Subscriber
    void onSuccessfulPaymentNotification(SuccessfulPaymentNotification notification) {
        // process notification
    }
}
```


# Latest releases

* 2019-02-21 **V4.0.0** : Properly opening and closing the client
* 2019-02-21 **V4.0.0** : BREAKING: Complete rewrite to use official Java client, Grails events and Grails 3.3.x. See [V3 README](README_3.x.md) for 3.x version reference.
* 2017-07-21 **V3.0.1** : Required API headers added for invoices download
* 2017-07-21 **V3.0.0** : Required API headers added + Grails upgraded to 3.2.11
* 2017-02-03 **V3.0.0-beta5** : WebHook controller logic fixed, added interceptor and url mapping + handlerBean renamed to match the webhook service name
* 2016-04-18 **V2.2.11** : API errors fully returned in RecurlyApiResponseException
* 2015-10-07 **V2.2.10** : Fix in Subscription processor to allow removing add-ons
* 2015-09-01 **V2.2.9** : Server reply updated with Customer error only in exceptions
* 2015-07-27 **V2.2.8** : Header added for webhook repost (with UTF-8 encoding)
* 2015-05-26 **V2.2.7** : Fix in WebHookNotificationProcessor to add add-ons to response
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
