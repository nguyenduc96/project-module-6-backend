package com.diosa.service.file;

import com.diosa.model.file.TaskFile;
import com.diosa.service.IBaseService;

import java.util.List;

public interface ITaskFileService extends IBaseService<TaskFile> {
    List<TaskFile> findAllByTaskId(Long taskId);

    void deleteByTaskId(Long id);

}
