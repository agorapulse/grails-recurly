package grails.plugin.recurly

import com.ning.billing.recurly.model.push.Notification
import grails.events.EventPublisher
import grails.util.GrailsNameUtils

class DefaultRecurlyPushNotificationService implements EventPublisher, RecurlyPushNotificationService {

    void dispatchNotification(Notification notification) {
        notify(GrailsNameUtils.getPropertyName(notification.class), notification)
    }

}
