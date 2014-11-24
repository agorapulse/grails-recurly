package grails.plugin.recurly.processors

import grails.plugin.recurly.RecurlySubscriptionAddOn
import grails.plugin.recurly.helpers.RecurlyProcessor

class RecurlySubscriptionAddOnProcessor extends RecurlyProcessor {

    public RecurlySubscriptionAddOnProcessor(RecurlySubscriptionAddOn recurlySubscriptionAddOn) {
        this.recurlySubscriptionAddOn = recurlySubscriptionAddOn
    }

    void checkConstraints() {
        checkProperty("addOnCode", MAX_SIZE_50, REQUIRED_FIELD, CAN_NOT_BE_BLANK)
        checkProperty("quantity", MAX_SIZE_50, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("unitAmountInCents", MAX_SIZE_50, OPTIONAL_FIELD, CAN_BE_BLANK)
    }

    public void setRecurlySubscriptionAddOn(RecurlySubscriptionAddOn recurlySubscriptionAddOn){
        this.beanUnderProcess = recurlySubscriptionAddOn
        beanClass = RecurlySubscriptionAddOn.class.toString()
    }

    public RecurlySubscriptionAddOn getRecurlySubscriptionAddOn(){
        return this.beanUnderProcess as RecurlySubscriptionAddOn
    }

    public String getDetailsInXML() {""}
}
