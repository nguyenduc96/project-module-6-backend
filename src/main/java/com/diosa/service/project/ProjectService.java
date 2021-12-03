package com.diosa.service.project;

import com.diosa.model.project.Project;
import com.diosa.repository.IProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService implements IProjectService{

    @Autowired
    private IProjectRepository projectRepository;

    @Override
    public Iterable<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public void remove(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<Project> findProjectByUserId(Long userId) {
        return projectRepository.findProjectByUserId(userId);
    }
}
