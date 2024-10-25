package com.example.e_commerce_app_backend.repository;

import com.example.e_commerce_app_backend.entities.Notification;
import com.example.e_commerce_app_backend.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification>  findAllByUserId(String userId);
    List<Notification> findAllByUserIdAndDate(String userId,LocalDateTime date);
    List<Notification> findAllByUserIdAndNotificationStatus(String userId, NotificationStatus status);
    List<Notification> findAllByUserIdAndDateAfter(String userId,LocalDateTime date);
    List<Notification> findAllByUserIdAndDateBefore(String userId,LocalDateTime date);
    List<Notification> findAllByUserIdAndDateBetween(String userId,LocalDateTime date1,LocalDateTime date2);

}
