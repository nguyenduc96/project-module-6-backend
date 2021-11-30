package com.diosa.service.task;

import com.diosa.model.task.Task;
import com.diosa.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements ITaskService{
    @Autowired
    private ITaskRepository taskRepository;

    @Override
    public Iterable<Task> findAll() {
        return taskRepository.findAllByOrderByPositionAsc();
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void remove(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> findAllByStatusIdOrderByPositionAsc(Long statusId) {
        return taskRepository.findAllByStatusIdOrderByPositionAsc(statusId);
    }
}
