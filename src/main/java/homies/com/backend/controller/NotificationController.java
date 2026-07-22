package homies.com.backend.controller;

import homies.com.backend.model.notification.Notification;
import homies.com.backend.security.CurrentUserUtil;
import homies.com.backend.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getNotifications(
            @PathVariable String userId) {

        currentUserUtil.requireSelfOrAdmin(userId);
        return ResponseEntity.ok(
                notificationService.getNotifications(userId)
        );
    }

    @PostMapping("/{notificationId}/read")
    public ResponseEntity<Notification> markAsRead(
            @PathVariable String notificationId) {

        currentUserUtil.requireSelfOrAdmin(
                notificationService.getNotificationById(notificationId).getUserId()
        );

        return ResponseEntity.ok(
                notificationService.markAsRead(notificationId)
        );
    }

    @GetMapping("/{userId}/unread-count")
    public ResponseEntity<Long> unreadCount(
            @PathVariable String userId) {

        currentUserUtil.requireSelfOrAdmin(userId);
        return ResponseEntity.ok(
                notificationService.getUnreadCount(userId)
       );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> clearNotifications(
            @PathVariable String userId) {

        currentUserUtil.requireSelfOrAdmin(userId);
        notificationService.clearAllNotifications(userId);

        return ResponseEntity.ok("Notifications Cleared");
    }
}
