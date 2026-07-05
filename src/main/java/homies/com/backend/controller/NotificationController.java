package homies.com.backend.controller;

import homies.com.backend.model.notification.Notification;
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

    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getNotifications(
            @PathVariable String userId) {

        return ResponseEntity.ok(
                notificationService.getNotifications(userId)
        );
    }

    @PostMapping("/{notificationId}/read")
    public ResponseEntity<Notification> markAsRead(
            @PathVariable String notificationId) {

        return ResponseEntity.ok(
                notificationService.markAsRead(notificationId)
        );
    }
}