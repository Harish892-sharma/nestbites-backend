package homies.com.backend.service;

import homies.com.backend.model.notification.Notification;

import java.util.List;

public interface NotificationService {

    Notification sendNotification(
            String userId,
            String userType,
            String title,
            String message,
            String type,
            String referenceId
    );

    List<Notification> getNotifications(String userId);

    Notification markAsRead(String notificationId);

    long getUnreadCount(String userId);

    void clearAllNotifications(String userId);
}