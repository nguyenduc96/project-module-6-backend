package com.diosa.service.file;

import com.diosa.model.file.TaskFile;
import com.diosa.repository.ITaskFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskFileService implements ITaskFileService{

    @Autowired
    private ITaskFileRepository taskFileRepository;

    @Override
    public Iterable<TaskFile> findAll() {
        return taskFileRepository.findAll();
    }

    @Override
    public Optional<TaskFile> findById(Long id) {
        return taskFileRepository.findById(id);
    }

    @Override
    public TaskFile save(TaskFile taskFile) {
        return taskFileRepository.save(taskFile);
    }

    @Override
    public void remove(Long id) {
        taskFileRepository.deleteById(id);
    }

    @Override
    public List<TaskFile> findAllByTaskId(Long taskId) {
        return taskFileRepository.findAllByTaskId(taskId);
    }

    @Override
    public void deleteByTaskId(Long id) {
        taskFileRepository.deleteAllByTaskId(id);
    }
}
