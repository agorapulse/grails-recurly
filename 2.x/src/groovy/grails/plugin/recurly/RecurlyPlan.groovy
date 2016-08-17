package grails.plugin.recurly

import grails.plugin.recurly.helpers.RecurlyRESTResource
import grails.plugin.recurly.processors.RecurlyPlanProcessor

class RecurlyPlan extends RecurlyRESTResource {

    String planCode
    String name
    String description
    String accountingCode
    Integer planIntervalLength
    String planIntervalUnit
    Integer trialIntervalLength
    String trialIntervalUnit
    Integer setupFeeInCents
    Integer unitAmountInCents
    Integer totalBillingCycles
    String unitName
    Boolean displayQuantity
    String successUrl
    String cancelUrl
    Date createdAt

    String delete() {
        remove(planCode)
    }

    RecurlyPlan save() {
        if (!createdAt) {
            create(this)
        } else {
            update(this)
        }
        return this
    }

    String toString() {
        "RecurlyPlan(planCode:'$planCode', name:'$name', unitAmountInCents:'$unitAmountInCents', createdAt:'$createdAt')"
    }

    // STATIC REST METHODS

    static String create(RecurlyPlan recurlyPlan) {
        handleResponse(new RecurlyPlanProcessor(recurlyPlan).create())
    }

    static RecurlyPlan fetch(String planCode) {
        handleResponse(new RecurlyPlanProcessor().getPlanDetails(planCode)) as RecurlyPlan
    }

    static List query(Map query = [:]) {
        if (query.max) query.per_page = query.max
        handleResponse(new RecurlyPlanProcessor().listPlans(query)) as List
    }

    static String remove(String planCode) {
        handleResponse(new RecurlyPlanProcessor().delete(planCode))
    }

    static RecurlyPlan update(RecurlyPlan recurlyPlan) {
        handleResponse(new RecurlyPlanProcessor(recurlyPlan).update()) as RecurlyPlan
    }
    
}