package com.diosa.service.notification;

import com.diosa.model.notification.Notification;
import com.diosa.repository.INotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService implements INotificationService {
    @Autowired
    private INotificationRepository notificationRepository;

    @Override
    public Iterable<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public void remove(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public List<Notification> findAllByReceiverIdOrderByIdDesc(Long id) {
        return notificationRepository.findAllByReceiverIdOrderByIdDesc(id);
    }
}
