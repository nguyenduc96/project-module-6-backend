package com.diosa.repository;

import com.diosa.model.file.TaskFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ITaskFileRepository extends JpaRepository<TaskFile, Long> {
    List<TaskFile> findAllByTaskId(Long id);

    @Modifying
    @Transactional
    void deleteAllByTaskId(Long id);

}
