package com.diosa.repository;

import com.diosa.model.status.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStatusRepository extends JpaRepository<Status, Long> {
    List<Status> findAllByBoardId(Long id);

    List<Status> findAllByBoardIdOrderByPositionAsc(Long boardId);
}
