package com.diosa.repository;

import com.diosa.model.permission.BoardPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface IBoardPermissionRepository extends JpaRepository<BoardPermission, Long> {
   BoardPermission findByUserIdAndBoardId(Long userId, Long boardId);
}

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM board_permission WHERE user_id = ?1 AND board_id = ?2", nativeQuery = true)
    void deleteByUserIdAndBoardId(Long userId, Long boardId);
}