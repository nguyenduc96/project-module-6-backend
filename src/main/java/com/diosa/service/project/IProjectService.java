package com.diosa.service.project;

import com.diosa.model.project.Project;
import com.diosa.service.IBaseService;

import java.util.List;

public interface IProjectService extends IBaseService<Project> {
    List<Project> findProjectByUserId(Long userId);
}
