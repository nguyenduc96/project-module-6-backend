package com.diosa.repository;

import com.diosa.model.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByProjectId(Long projectId);
}
