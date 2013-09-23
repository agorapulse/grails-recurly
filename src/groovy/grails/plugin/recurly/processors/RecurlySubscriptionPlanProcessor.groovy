package grails.plugin.recurly.processors

import grails.plugin.recurly.RecurlySubscriptionPlan
import groovy.xml.MarkupBuilder
import grails.plugin.recurly.templates.Response
import grails.plugin.recurly.helpers.RecurlyURLBuilder
import grails.plugin.recurly.enums.RecurlyUrlActionType
import grails.plugin.recurly.helpers.RecurlyProcessor

class RecurlySubscriptionPlanProcessor extends RecurlyProcessor {

    public RecurlySubscriptionPlanProcessor(RecurlySubscriptionPlan recurlySubscriptionPlan) {
        this.recurlySubscriptionPlan = recurlySubscriptionPlan
    }

    public RecurlySubscriptionPlanProcessor() {
        recurlySubscriptionPlan = new RecurlySubscriptionPlan()
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

    public Response<RecurlySubscriptionPlan> create() {
        Response<RecurlySubscriptionPlan> response = new Response<RecurlySubscriptionPlan>()
        response.entity = recurlySubscriptionPlan

        if (this.validate()) {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.CREATE_PLAN)
            this.processUsingMethodPOST()
            updateResponse(httpResponse.entity.getData())
            response.entity = recurlySubscriptionPlan
            response.status = httpResponse.status
            response.message = "This Response is Generated Against CREATE Request. " + httpResponse?.message
            response.errors = httpResponse.errors
        } else {
            response.status = "error"
            response.errors = this.errors()
            response.message = "Validation Of Feilds Failed, See Errors Map For Details"
        }
        return response
    }

    public Response<RecurlySubscriptionPlan> update() {
        Response<RecurlySubscriptionPlan> response = new Response<RecurlySubscriptionPlan>()
        response.entity = recurlySubscriptionPlan

        if (this.validate()) {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.UPDATE_PLAN, recurlySubscriptionPlan.planCode)
            this.processUsingMethodPUT()
            updateResponse(httpResponse.entity.getData())
            response.entity = recurlySubscriptionPlan
            response.status = httpResponse.status
            response.message = "This Response is Generated Against UPDATE Request. " + httpResponse?.message
            response.errors = httpResponse.errors
        } else {
            response.status = "error"
            response.errors = this.errors()
            response.message = "Validation Of Feilds Failed, See Errors Map For Details"
        }
        return response
    }

    public Response<RecurlySubscriptionPlan> delete() {
        Response<RecurlySubscriptionPlan> response = new Response<RecurlySubscriptionPlan>()
        response.entity = recurlySubscriptionPlan

        if (this.recurlySubscriptionPlan.planCode) {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.DELETE_PLAN, recurlySubscriptionPlan.planCode)
            this.processUsingMethodDELETE()
            updateResponse(httpResponse.entity.getData())
            response.entity = recurlySubscriptionPlan
            response.status = httpResponse.status
            response.message = "This Response is Generated Against DELETE Request. " + httpResponse?.message
            response.errors = httpResponse.errors
        } else {
            this.validate()
            response.status = "error"
            response.errors = this.errors()
            response.message = "PlanCode Feild is required."
        }
        return response
    }

    public Response<List<RecurlySubscriptionPlan>> listAllSubscriptionPlans() {
        Response<List<RecurlySubscriptionPlan>> response = new Response<List<RecurlySubscriptionPlan>>()
        List<RecurlySubscriptionPlan> recurlySubscriptionPlans = []
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.PLANS)
        this.processUsingMethodGET()
        httpResponse.entity.getData()?.plan?.each {
            recurlySubscriptionPlans.add(new RecurlySubscriptionPlan(
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
        response.entity = recurlySubscriptionPlans
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against LIST_ALL_SUBSCRYPTION_PLANS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public Response<RecurlySubscriptionPlan> getSubscriptionPlanDetails(String planCode) {
        Response<RecurlySubscriptionPlan> response = new Response<RecurlySubscriptionPlan>()
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.GET_PLAN_DETAILS, planCode)
        this.processUsingMethodGET()
        updateResponse(httpResponse.entity.getData())
        response.entity = recurlySubscriptionPlan
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
            "plan_code"(recurlySubscriptionPlan.planCode)
            "name"(recurlySubscriptionPlan.name)
            "description"(recurlySubscriptionPlan.description ?: "")
            "success_url"(recurlySubscriptionPlan.successUrl ?: "")
            "cancel_url"(recurlySubscriptionPlan.cancelUrl ?: "")
            "unit_amount_in_cents"(recurlySubscriptionPlan.unitAmountInCents)
            "setup_fee_in_cents"(recurlySubscriptionPlan.setupFeeInCents)
            "plan_interval_length"(recurlySubscriptionPlan.planIntervalLength)
            "plan_interval_unit"(recurlySubscriptionPlan.planIntervalUnit)
            "trial_interval_length"(recurlySubscriptionPlan.trialIntervalLength)
            "trial_interval_unit"(recurlySubscriptionPlan.trialIntervalUnit)
        }
        return writer.toString()
    }

    private void updateResponse(Object responseData) {
        if (!responseData) {
            return
        }
        this.recurlySubscriptionPlan.planCode = responseData.plan_code
        this.recurlySubscriptionPlan.name = responseData.name
        this.recurlySubscriptionPlan.description = responseData.description
        this.recurlySubscriptionPlan.successUrl = responseData.success_url
        this.recurlySubscriptionPlan.cancelUrl = responseData.cancel_url
        this.recurlySubscriptionPlan.createdAt = responseData.created_at
        this.recurlySubscriptionPlan.unitAmountInCents = convertNodeToInteger(responseData.unit_amount_in_cents)
        this.recurlySubscriptionPlan.setupFeeInCents = convertNodeToInteger(responseData.setup_fee_in_cents)
        this.recurlySubscriptionPlan.planIntervalLength = convertNodeToInteger(responseData.plan_interval_length)
        this.recurlySubscriptionPlan.planIntervalUnit = responseData.plan_interval_unit
        this.recurlySubscriptionPlan.trialIntervalLength = convertNodeToInteger(responseData.trial_interval_length)
        this.recurlySubscriptionPlan.trialIntervalUnit = responseData.trial_interval_unit
    }

    public void setRecurlySubscriptionPlan(RecurlySubscriptionPlan recurlySubscriptionPlan) {
        this.beanUnderProcess = recurlySubscriptionPlan
        beanClass = RecurlySubscriptionPlan.class
    }

    public RecurlySubscriptionPlan getRecurlySubscriptionPlan() {
        return this.beanUnderProcess as RecurlySubscriptionPlan
    }
}
