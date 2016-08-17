package grails.plugin.recurly

class RecurlyCreditCard {

    String creditCardNumber
    String verificationValue
    String year
    String month

    //Read only properties
    String firstSix
    String lastFour
    String type

    String toString() {
        "RecurlyCreditCard(type:'$type', firstSix:'$firstSix', lastFour:'$lastFour')"
    }

}