package com.diosa.repository;

import com.diosa.model.project.Project;
import com.diosa.model.project.ProjectResponse;
import com.diosa.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IProjectRepository extends JpaRepository<Project, Long> {
    @Transactional
    @Query(value = "SELECT * FROM Project p JOIN project_users pu ON p.id = pu.project_id WHERE pu.users_id = ?1", nativeQuery = true)
    List<Project> findProjectByUserId(Long userId);

    List<Project> findAllByProjectOwner(User projectOwner);
}
