package grails.plugin.recurly

class RecurlyCreditCard {

    String creditCardNumber
    String verificationValue
    String year
    String month

    //Read only properties
    String lastFour
    String type
    String startMonth
    String startYear
    String issueNumber

    String toString() {
        "RecurlyCreditCard(lastFour:'$lastFour', type:'$type', startMonth:'$startMonth', startYear:'$startYear')"
    }

}