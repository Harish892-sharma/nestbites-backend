package homies.com.backend.service.impl;

import homies.com.backend.model.notification.Notification;
import homies.com.backend.repository.NotificationRepository;
import homies.com.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification sendNotification(
            String userId,
            String userType,
            String title,
            String message,
            String type,
            String referenceId) {

        Notification notification = new Notification();

        notification.setUserId(userId);
        notification.setUserType(userType);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType(type);
        notification.setReferenceId(referenceId);

        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotifications(String userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public Notification markAsRead(String notificationId) {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setRead(true);

        return notificationRepository.save(notification);
    }

    @Override
    public long getUnreadCount(String userId) {
        return notificationRepository.countByUserIdAndReadFalse(userId);
    }

    @Override
    public void clearAllNotifications(String userId) {
        notificationRepository.deleteByUserId(userId);
    }
}