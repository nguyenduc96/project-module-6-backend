package com.diosa.repository;

import com.diosa.model.file.TaskFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaskFileRepository extends JpaRepository<TaskFile, Long> {
}
