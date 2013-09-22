package org.devunited.grails.plugin.recurly.processors

import org.devunited.grails.plugin.recurly.RecurlyCreditCard
import org.devunited.grails.plugin.recurly.helpers.RecurlyProcessor

class RecurlyCreditCardProcessor extends RecurlyProcessor {

    public RecurlyCreditCardProcessor(RecurlyCreditCard recurlyCreditCard) {
        this.recurlyCreditCard = recurlyCreditCard
    }

    void checkConstraints() {
        checkProperty("creditCardNumber", MAX_SIZE_16, REQUIRED_FIELD, CAN_NOT_BE_BLANK, MIN_SIZE_15)
        checkProperty("verificationValue", MAX_SIZE_4, REQUIRED_FIELD, CAN_NOT_BE_BLANK, MIN_SIZE_3)
        checkProperty("year", MAX_SIZE_4, REQUIRED_FIELD, CAN_NOT_BE_BLANK, MIN_SIZE_4)
        checkProperty("month", MAX_SIZE_2, REQUIRED_FIELD, CAN_NOT_BE_BLANK, MIN_SIZE_1)
    }

    public void setRecurlyCreditCard(RecurlyCreditCard recurlyCreditCard) {
        this.beanUnderProcess = recurlyCreditCard
        beanClass = RecurlyCreditCard.class
    }

    public RecurlyCreditCard getRecurlyCreditCard() {
        return this.beanUnderProcess as RecurlyCreditCard
    }

    public String getDetailsInXML() {""}
}