package com.diosa.service.project;

import com.diosa.model.project.Project;
import com.diosa.model.project.ProjectResponse;
import com.diosa.model.user.User;
import com.diosa.service.IBaseService;

import java.util.List;

public interface IProjectService extends IBaseService<Project> {
    List<Project> findProjectByUserId(Long userId);

    ProjectResponse findProjectById(Long id);

    List<Project> findProjectByProjectOwner(User user);


}
