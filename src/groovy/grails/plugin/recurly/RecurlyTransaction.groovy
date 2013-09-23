package grails.plugin.recurly

class RecurlyTransaction {

    String id
    String accountCode
    String type
    String action
    String date
    String status
    String message
    String reference
    String cvvResult
    String avsResult
    String avsResultPostal
    String avsResultStreet
    Integer amountInCents
    Boolean test
    Boolean voidable
    Boolean refundable

}
