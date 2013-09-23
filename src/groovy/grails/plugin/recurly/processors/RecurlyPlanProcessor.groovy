package grails.plugin.recurly.processors

import grails.plugin.recurly.RecurlyPlan
import grails.plugin.recurly.enums.RecurlyUrlActionType
import grails.plugin.recurly.helpers.RecurlyProcessor
import grails.plugin.recurly.helpers.RecurlyURLBuilder
import grails.plugin.recurly.templates.Response
import groovy.xml.MarkupBuilder

class RecurlyPlanProcessor extends RecurlyProcessor {

    public RecurlyPlanProcessor(RecurlyPlan recurlyPlan) {
        this.recurlyPlan = recurlyPlan
    }

    public RecurlyPlanProcessor() {
        recurlyPlan = new RecurlyPlan()
    }

    void checkConstraints() {
        checkProperty("planCode", MAX_SIZE_50, REQUIRED_FIELD, CAN_NOT_BE_BLANK)
        checkProperty("name", MAX_SIZE_255, REQUIRED_FIELD, CAN_NOT_BE_BLANK)
        checkProperty("description", MAX_SIZE_TEXT, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("successUrl", MAX_SIZE_255, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("cancelUrl", MAX_SIZE_255, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("unitAmountInCents", MAX_SIZE_50, REQUIRED_FIELD, CAN_NOT_BE_BLANK)
        checkProperty("setupFeeInCents", MAX_SIZE_50, REQUIRED_FIELD, CAN_NOT_BE_BLANK)
        checkProperty("planIntervalLength", MAX_SIZE_50, REQUIRED_FIELD, CAN_NOT_BE_BLANK)
        checkProperty("trialIntervalLength", MAX_SIZE_50, REQUIRED_FIELD, CAN_NOT_BE_BLANK)
        checkProperty("planIntervalUnit", MAX_SIZE_10, REQUIRED_FIELD, CAN_NOT_BE_BLANK)
        checkProperty("trialIntervalUnit", MAX_SIZE_10, REQUIRED_FIELD, CAN_NOT_BE_BLANK)
    }

    public Response<RecurlyPlan> create() {
        Response<RecurlyPlan> response = new Response<RecurlyPlan>()
        response.entity = recurlyPlan

        if (this.validate()) {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.CREATE_PLAN)
            this.processUsingMethodPOST()
            updateResponse(httpResponse.entity.getData())
            response.entity = recurlyPlan
            response.status = httpResponse.status
            response.message = "This Response is Generated Against CREATE Request. " + httpResponse?.message
            response.errors = httpResponse.errors
        } else {
            response.status = "error"
            response.errors = this.errors()
            response.message = "Validation Of Fields Failed, See Errors Map For Details"
        }
        return response
    }

    public Response<RecurlyPlan> update() {
        Response<RecurlyPlan> response = new Response<RecurlyPlan>()
        response.entity = recurlyPlan

        if (this.validate()) {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.UPDATE_PLAN, recurlyPlan.planCode)
            this.processUsingMethodPUT()
            updateResponse(httpResponse.entity.getData())
            response.entity = recurlyPlan
            response.status = httpResponse.status
            response.message = "This Response is Generated Against UPDATE Request. " + httpResponse?.message
            response.errors = httpResponse.errors
        } else {
            response.status = "error"
            response.errors = this.errors()
            response.message = "Validation Of Fields Failed, See Errors Map For Details"
        }
        return response
    }

    public Response<String> delete(String planCode) {
        Response<String> response = new Response<String>()
        response.entity = planCode

        if (planCode) {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.DELETE_PLAN, planCode)
            this.processUsingMethodDELETE()
            updateResponse(httpResponse.entity.getData())
            response.status = httpResponse.status
            response.message = "This Response is Generated Against DELETE Request. " + httpResponse?.message
            response.errors = httpResponse.errors
        } else {
            this.validate()
            response.status = "error"
            response.errors = this.errors()
            response.message = "PlanCode Field is required."
        }
        return response
    }

    public Response<List<RecurlyPlan>> listPlans() {
        Response<List<RecurlyPlan>> response = new Response<List<RecurlyPlan>>()
        List<RecurlyPlan> recurlyPlans = []
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.PLANS)
        this.processUsingMethodGET()
        httpResponse.entity.getData()?.plan?.each {
            recurlyPlans.add(new RecurlyPlan(
                    planCode: it.plan_code,
                    name: it.name,
                    description: it.description,
                    successUrl: it.success_url,
                    cancelUrl: it.cancel_url,
                    createdAt: it.created_at,
                    unitAmountInCents: convertNodeToInteger(it.unit_amount_in_cents),
                    setupFeeInCents: convertNodeToInteger(it.setup_fee_in_cents),
                    planIntervalLength: convertNodeToInteger(it.plan_interval_length),
                    planIntervalUnit: it.plan_interval_unit,
                    trialIntervalLength: convertNodeToInteger(it.trial_interval_length),
                    trialIntervalUnit: it.trial_interval_unit
            ))
        }
        response.entity = recurlyPlans
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against LIST_ALL_SUBSCRYPTION_PLANS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public Response<RecurlyPlan> getPlanDetails(String planCode) {
        Response<RecurlyPlan> response = new Response<RecurlyPlan>()
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.GET_PLAN_DETAILS, planCode)
        this.processUsingMethodGET()
        updateResponse(httpResponse.entity.getData())
        response.entity = recurlyPlan
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_SUBSCRIPTION_PLAN_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public String getDetailsInXML() {
        StringWriter writer = new StringWriter()
        writer.write '<?xml version="1.0"?>\n'
        MarkupBuilder xml = new MarkupBuilder(writer)

        xml.plan() {
            "plan_code"(recurlyPlan.planCode)
            "name"(recurlyPlan.name)
            "description"(recurlyPlan.description ?: "")
            "accountingCode"(recurlyPlan.accountingCode ?: "")
            "plan_interval_length"(recurlyPlan.planIntervalLength)
            "plan_interval_unit"(recurlyPlan.planIntervalUnit)
            "trial_interval_length"(recurlyPlan.trialIntervalLength)
            "trial_interval_unit"(recurlyPlan.trialIntervalUnit)
            "setup_fee_in_cents"(recurlyPlan.setupFeeInCents)
            "unit_amount_in_cents"(recurlyPlan.unitAmountInCents)
            "total_billing_cycles"(recurlyPlan.totalBillingCycles)
            "unit_name"(recurlyPlan.unitName)
            "display_quantity"(recurlyPlan.displayQuantity)
            "success_url"(recurlyPlan.successUrl ?: "")
            "cancel_url"(recurlyPlan.cancelUrl ?: "")
        }
        return writer.toString()
    }

    private void updateResponse(Object responseData) {
        if (!responseData) {
            return
        }
        this.recurlyPlan.planCode = responseData.plan_code
        this.recurlyPlan.name = responseData.name
        this.recurlyPlan.description = responseData.description
        this.recurlyPlan.accountingCode = responseData.accounting_code
        this.recurlyPlan.planIntervalLength = convertNodeToInteger(responseData.plan_interval_length)
        this.recurlyPlan.planIntervalUnit = responseData.plan_interval_unit
        this.recurlyPlan.trialIntervalLength = convertNodeToInteger(responseData.trial_interval_length)
        this.recurlyPlan.trialIntervalUnit = responseData.trial_interval_unit
        this.recurlyPlan.setupFeeInCents = convertNodeToInteger(responseData.setup_fee_in_cents)
        this.recurlyPlan.unitAmountInCents = convertNodeToInteger(responseData.unit_amount_in_cents)
        this.recurlyPlan.totalBillingCycles = convertNodeToInteger(responseData.total_billing_cycles)
        this.recurlyPlan.unitName = responseData.unit_name
        this.recurlyPlan.displayQuantity = responseData.display_quantity as Boolean
        this.recurlyPlan.successUrl = responseData.success_url
        this.recurlyPlan.cancelUrl = responseData.cancel_url
        this.recurlyPlan.createdAt = responseData.created_at
    }

    public void setRecurlyPlan(RecurlyPlan recurlyPlan) {
        this.beanUnderProcess = recurlyPlan
        beanClass = RecurlyPlan.class
    }

    public RecurlyPlan getRecurlyPlan() {
        return this.beanUnderProcess as RecurlyPlan
    }
}
