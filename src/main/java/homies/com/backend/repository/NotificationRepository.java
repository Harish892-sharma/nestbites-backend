package homies.com.backend.repository;

import homies.com.backend.model.notification.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    // All Notifications
    List<Notification> findByUserIdOrderByCreatedAtDesc(String userId);

    // Unread Notifications
    List<Notification> findByUserIdAndReadFalse(String userId);

    // Count Unread
    long countByUserIdAndReadFalse(String userId);

    // Delete All
    void deleteByUserId(String userId);
}