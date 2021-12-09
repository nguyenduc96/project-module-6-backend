package com.diosa.repository;

import com.diosa.model.assign.Assign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IAssignRepository extends JpaRepository<Assign, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM assign WHERE user_id = ?1 AND task_id = ?2", nativeQuery = true)
    void deleteAssign(Long userId, Long taskId);
}
