package com.diosa.service.task;

import com.diosa.model.task.Task;
import com.diosa.service.IBaseService;

import java.util.List;

public interface ITaskService extends IBaseService<Task> {
    List<Task> findAllByStatusIdOrderByPositionAsc(Long statusId);

    void deleteAllByStatusId(Long id);

    List<Task> findAllByStatusIdAndTitleContainsOrderByPositionAsc(Long statusId, String title);
}
