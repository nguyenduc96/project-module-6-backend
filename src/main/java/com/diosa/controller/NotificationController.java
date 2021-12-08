package com.diosa.controller;

import com.diosa.model.notification.Notification;
import com.diosa.service.notification.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private INotificationService notificationService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Notification>> getAll(@PathVariable Long id) {
        return new ResponseEntity<>(notificationService.findAllByReceiverIdOrderByIdDesc(id), HttpStatus.OK);
    }

}
