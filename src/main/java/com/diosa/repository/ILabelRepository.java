package com.diosa.repository;

import com.diosa.model.label.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ILabelRepository extends JpaRepository<Label, Long> {
    List<Label> findAllByBoardId(Long id);

    @Modifying
    @Transactional
    void deleteAllByBoardId(Long id);

    @Query(value = "SELECT * FROM label l JOIN task_labels tl ON l.id = tl.labels_id WHERE tl.task_id = ?1", nativeQuery = true)
    List<Label> findAllByTaskId(Long id);
}
