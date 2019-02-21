package grails.plugin.recurly;

import com.ning.billing.recurly.model.push.Notification;

public interface RecurlyPushNotificationService {
    void dispatchNotification(Notification notification);

}
