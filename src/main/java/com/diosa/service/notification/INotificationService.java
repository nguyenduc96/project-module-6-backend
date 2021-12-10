package com.diosa.service.notification;

import com.diosa.model.notification.Notification;
import com.diosa.service.IBaseService;

import java.util.List;

public interface INotificationService extends IBaseService<Notification> {
    List<Notification> findAllByReceiverIdOrderByIdDesc(Long id);
}
