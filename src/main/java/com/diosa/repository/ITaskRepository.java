package com.diosa.repository;

import com.diosa.model.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByOrderByPositionAsc();

    List<Task> findAllByStatusIdOrderByPositionAsc(Long statusId);

    @Modifying
    @Transactional
//    @Query(value = "DELETE FROM Task t where t.status_id = ?1", nativeQuery = true)
    void deleteAllByStatusId(Long id);
}
