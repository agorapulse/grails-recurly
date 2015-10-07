package grails.plugin.recurly.processors

import grails.plugin.recurly.RecurlyAccount
import grails.plugin.recurly.RecurlySubscription
import grails.plugin.recurly.RecurlySubscriptionAddOn
import grails.plugin.recurly.RecurlySubscriptionPendingChanges
import grails.plugin.recurly.enums.RecurlySubscriptionChangeTimeFrame
import grails.plugin.recurly.enums.RecurlySubscriptionRefund
import grails.plugin.recurly.enums.RecurlySubscriptionState
import grails.plugin.recurly.enums.RecurlyUrlActionType
import grails.plugin.recurly.helpers.RecurlyProcessor
import grails.plugin.recurly.helpers.RecurlyURLBuilder
import grails.plugin.recurly.templates.Response
import groovy.xml.MarkupBuilder

class RecurlySubscriptionProcessor extends RecurlyProcessor {

    private RecurlySubscriptionChangeTimeFrame recurlySubscriptionChangeTimeFrame = null

    public RecurlySubscriptionProcessor(RecurlySubscription recurlySubscription) {
        this.recurlySubscription = recurlySubscription
    }

    public RecurlySubscriptionProcessor() {
        recurlySubscription = new RecurlySubscription()
    }

    void checkConstraints() {
        checkProperty("planCode", MAX_SIZE_50, REQUIRED_FIELD, CAN_NOT_BE_BLANK)
        checkProperty("couponCode", MAX_SIZE_50, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("trialEndsAt", MAX_SIZE_50, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("unitAmountInCents", MAX_SIZE_50, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("quantity", MAX_SIZE_50, OPTIONAL_FIELD, CAN_BE_BLANK)
        propertiesWithErrors.putAll(new RecurlyBillingInfoProcessor(recurlySubscription.billingInfo).errors())
        propertiesWithErrors.putAll(new RecurlyAccountProcessor(recurlySubscription.account).errors())
        recurlySubscription.addOns.each {addOn ->
            propertiesWithErrors.putAll(new RecurlySubscriptionAddOnProcessor(addOn).errors())
        }
    }

    public Response<RecurlySubscription> create() {
        Response<RecurlySubscription> response = new Response<RecurlySubscription>()
        response.entity = recurlySubscription

        if (this.validate()) {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.CREATE_SUBSCRIPTION)
            this.processUsingMethodPOST()
            updateResponse(httpResponse.entity.getData())
            response.entity = recurlySubscription
            response.status = httpResponse?.status
            response.message = "This Response is Generated Against CREATE Request. " + httpResponse?.message
            response.errors = httpResponse?.errors
        } else {
            response.status = "error"
            response.errors = this.errors()
            response.message = "Validation Of Fields Failed, See Errors Map For Details"
        }
        return response
    }

    public Response<String> cancel(String subscriptionUuid) {
        Response<String> response = new Response<String>()
        response.entity = subscriptionUuid

        if (subscriptionUuid) {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.CANCEL_SUBSCRIPTION, subscriptionUuid)
            this.processUsingMethodPUT(false)
            response.status = httpResponse?.status
            response.message = "This Response is Generated Against PUT Request. " + httpResponse?.message
            response.errors = httpResponse?.errors
        } else {
            response.status = "error"
            response.errors = ["AccountCode": "Is Null"]
            response.message = "Validation Of Fields Failed, See Errors Map For Details"
        }
        return response
    }

    public Response<String> reactivate(String subscriptionUuid) {
        Response<String> response = new Response<String>()
        response.entity = subscriptionUuid

        if (subscriptionUuid) {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.REACTIVATE_SUBSCRIPTION, subscriptionUuid)
            this.processUsingMethodPUT(false)
            response.status = httpResponse?.status
            response.message = "This Response is Generated Against PUT Request. " + httpResponse?.message
            response.errors = httpResponse?.errors
        } else {
            response.status = "error"
            response.errors = ["AccountCode": "Is Null"]
            response.message = "Validation Of Fields Failed, See Errors Map For Details"
        }
        return response
    }

    public Response<String> terminate(String subscriptionUuid, RecurlySubscriptionRefund refund) {
        Response<String> response = new Response<String>()
        response.entity = subscriptionUuid
        RecurlyUrlActionType recurlyUrlActionType
        switch(refund) {
            case RecurlySubscriptionRefund.NONE:
                recurlyUrlActionType = RecurlyUrlActionType.DELETE_SUBSCRIPTION_INSTANTLY_WITHOUT_REFUND
                break
            case RecurlySubscriptionRefund.FULL:
                recurlyUrlActionType = RecurlyUrlActionType.DELETE_SUBSCRIPTION_INSTANTLY_WITH_FULL_REFUND
                break
            case RecurlySubscriptionRefund.PARTIAL:
                recurlyUrlActionType = RecurlyUrlActionType.DELETE_SUBSCRIPTION_INSTANTLY_WITH_PARTIAL_REFUND
                break
        }
        if (subscriptionUuid) {
            this.targetUrl = RecurlyURLBuilder.buildURL(recurlyUrlActionType, subscriptionUuid)
            this.processUsingMethodPUT(false)
            response.status = httpResponse?.status
            response.message = "This Response is Generated Against PUT Request. " + httpResponse?.message
            response.errors = httpResponse?.errors
        } else {
            response.status = "error"
            response.errors = ["AccountCode": "Is Null"]
            response.message = "Validation Of Fields Failed, See Errors Map For Details"
        }
        return response
    }

    public Response<RecurlySubscription> update(RecurlySubscriptionChangeTimeFrame recurlySubscriptionChangeTimeFrame) {
        this.recurlySubscriptionChangeTimeFrame = recurlySubscriptionChangeTimeFrame
        Response<RecurlySubscription> response = new Response<RecurlySubscription>()
        response.entity = recurlySubscription
        if (recurlySubscription.uuid) {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.UPDATE_SUBSCRIPTION, recurlySubscription.uuid)
            this.processUsingMethodPUT()
            updateResponse(httpResponse.entity.getData())
            response.entity = recurlySubscription
            response.status = httpResponse?.status
            response.message = "This Response is Generated Against UPDATE Request. " + httpResponse?.message
            response.errors = httpResponse?.errors
        } else {
            response.status = "error"
            response.errors = ["AccountCode": "Is Null"]
            response.message = "Validation Of Fields Failed, See Errors Map For Details"
        }
        this.recurlySubscriptionChangeTimeFrame = null
        return response
    }

    public Response<RecurlySubscription> getSubscriptionDetails(String subscriptionUuid) {
        Response<RecurlySubscription> response = new Response<RecurlySubscription>()
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.GET_SUBSCRIPTION_DETAILS, subscriptionUuid)
        this.processUsingMethodGET()
        if (httpResponse.status == '200') {
            updateResponse(httpResponse.entity.getData())
        }
        response.entity = recurlySubscription
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_SUBSCRIPTION_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public void setRecurlySubscription(RecurlySubscription recurlySubscription) {
        this.beanUnderProcess = recurlySubscription
        beanClass = RecurlySubscription.class.toString()
    }

    public RecurlySubscription getRecurlySubscription() {
        return this.beanUnderProcess as RecurlySubscription
    }

    public String getDetailsInXML() {
        StringWriter writer = new StringWriter()
        writer.write '<?xml version="1.0" encoding="UTF-8"?>\n'
        MarkupBuilder xml = new MarkupBuilder(writer)

        xml.subscription() {
            if (recurlySubscriptionChangeTimeFrame) {
                "timeframe"(recurlySubscriptionChangeTimeFrame.value)
            }
            "plan_code"(recurlySubscription.planCode)
            if (recurlySubscription.currency) {
                "currency"(recurlySubscription.currency)
            }
            if (recurlySubscription.quantity) {
                "quantity"(recurlySubscription.quantity ?: "")
            }
            if (recurlySubscription.unitAmountInCents) {
                "unit_amount_in_cents"(recurlySubscription.unitAmountInCents ?: "")
            }
            "subscription_add_ons"() {
                recurlySubscription.addOns.each {addOn ->
                    "subscription_add_on"() {
                        "add_on_code"(addOn.addOnCode)
                        if (addOn.quantity) {
                            "quantity"(addOn.quantity ?: "")
                        }
                        if (addOn.unitAmountInCents) {
                            "unit_amount_in_cents"(addOn.unitAmountInCents ?: "")
                        }
                    }
                }
            }
            if (!recurlySubscription.uuid) {
                // Only to create subscription
                if (recurlySubscription.couponCode) {
                    "coupon_code"(recurlySubscription.couponCode ?: "")
                }
                if (recurlySubscription.trialEndsAt) {
                    "trial_ends_at"(recurlySubscription.trialEndsAt ?: "")
                }
                "account"() {
                    "account_code"(recurlySubscription.account.accountCode)
                    if (recurlySubscription.account.userName) {
                        "username"(recurlySubscription.account.userName ?: "")
                    }
                    if (recurlySubscription.account.firstName) {
                        "first_name"(recurlySubscription.account.firstName ?: "")
                    }
                    if (recurlySubscription.account.lastName) {
                        "last_name"(recurlySubscription.account.lastName ?: "")
                    }
                    if (recurlySubscription.account.email) {
                        "email"(recurlySubscription.account.email ?: "")
                    }
                    if (recurlySubscription.account.companyName) {
                        "company_name"(recurlySubscription.account.companyName ?: "")
                    }
                    if (recurlySubscription.billingInfo) {
                        "billing_info"() {
                            if (recurlySubscription.billingInfo.tokenId) {
                                "token_id"(recurlySubscription.billingInfo.tokenId ?: "")
                            } else {
                                "first_name"(recurlySubscription.billingInfo.firstName)
                                "last_name"(recurlySubscription.billingInfo.lastName)
                                if (recurlySubscription.billingInfo.address1) {
                                    "address1"(recurlySubscription.billingInfo.address1 ?: "")
                                }
                                if (recurlySubscription.billingInfo.address2) {
                                    "address2"(recurlySubscription.billingInfo.address2 ?: "")
                                }
                                if (recurlySubscription.billingInfo.city) {
                                    "city"(recurlySubscription.billingInfo.city ?: "")
                                }
                                if (recurlySubscription.billingInfo.state) {
                                    "state"(recurlySubscription.billingInfo.state ?: "")
                                }
                                if (recurlySubscription.billingInfo.zip) {
                                    "zip"(recurlySubscription.billingInfo.zip ?: "")
                                }
                                if (recurlySubscription.billingInfo.country) {
                                    "country"(recurlySubscription.billingInfo.country ?: "")
                                }
                                if (recurlySubscription.billingInfo.ipAddress) {
                                    "ip_address"(recurlySubscription.billingInfo.ipAddress ?: "")
                                }
                                "credit_card"() {
                                    "number"(recurlySubscription.billingInfo.creditCard.creditCardNumber)
                                    "verification_value"(recurlySubscription.billingInfo.creditCard.verificationValue)
                                    "year"(recurlySubscription.billingInfo.creditCard.year)
                                    "month"(recurlySubscription.billingInfo.creditCard.month)
                                }
                            }
                        }
                    }
                }
            }
        }
        return writer.toString()
    }

    private void updateResponse(Object responseData) {
        if (!responseData) {
            return
        }
        recurlySubscription.uuid = responseData.uuid
        if (responseData.account['@href'] && responseData.account['@href'] != '') {
            recurlySubscription.accountCode = responseData.account['@href']?.toString().tokenize('/')?.last()
        }
        if (responseData.coupon_code) {
            recurlySubscription.couponCode = responseData.coupon_code
        }
        if (responseData.trial_ends_at) {
            recurlySubscription.trialEndsAt = convertNodeToDate(responseData.trial_ends_at)
        }
        if (responseData.unit_amount_in_cents) {
            recurlySubscription.unitAmountInCents = convertNodeToInteger(responseData.unit_amount_in_cents)
        }
        if (responseData.quantity) {
            recurlySubscription.quantity = convertNodeToInteger(responseData.quantity)
        }
        if (responseData.currency) {
            recurlySubscription.currency = responseData.currency
        }
        if (responseData.plan.name) {
            recurlySubscription.planName = responseData.plan.name
        }
        if (responseData.plan.plan_code) {
            recurlySubscription.planCode = responseData.plan.plan_code
        }
        if (responseData.state && responseData.state != '') {
            try {
                recurlySubscription.state = responseData.state.toString().toUpperCase() as RecurlySubscriptionState
            } catch (Exception e) {
                recurlySubscription.state = responseData.state
            }
        }
        if (responseData.activated_at) {
            recurlySubscription.activatedAt = convertNodeToDate(responseData.activated_at)
        }
        if (responseData.canceled_at) {
            recurlySubscription.cancelledAt = convertNodeToDate(responseData.canceled_at)
        }
        if (responseData.expires_at) {
            recurlySubscription.expiresAt = convertNodeToDate(responseData.expires_at)
        }
        if (responseData.current_period_started_at) {
            recurlySubscription.currentPeriodStartedAt = convertNodeToDate(responseData.current_period_started_at)
        }
        if (responseData.current_period_ends_at) {
            recurlySubscription.currentPeriodEndsAt = convertNodeToDate(responseData.current_period_ends_at)
        }
        if (responseData.trial_started_at) {
            recurlySubscription.trialStartedAt = convertNodeToDate(responseData.trial_started_at)
        }
        if (responseData.pending_subscription) {
            recurlySubscription.pendingChanges = recurlySubscription.pendingChanges ?: new RecurlySubscriptionPendingChanges()
            if (responseData.pending_subscription.plan_code) {
                recurlySubscription.pendingChanges.planCode = responseData.pending_subscription.plan_code
            }
            if (responseData.pending_subscription.quantity) {
                recurlySubscription.pendingChanges.quantity = convertNodeToInteger(responseData.pending_subscription.quantity)
            }
            if (responseData.pending_subscription.activates_at) {
                recurlySubscription.pendingChanges.activatesAt = convertNodeToDate(responseData.pending_subscription.activates_at)
            }
        }
        responseData.subscription_add_ons?.each {addOn ->
            recurlySubscription.addOns.add(new RecurlySubscriptionAddOn(addOnCode: addOn.add_on_code, quantity: convertNodeToInteger(addOn.quantity), unitAmountInCents: convertNodeToInteger(addOn.unit_amount_in_cents)))
        }
    }

}