package com.diosa.repository;

import com.diosa.model.permission.BoardPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IBoardPermissionRepository extends JpaRepository<BoardPermission, Long> {
   BoardPermission findByUserIdAndBoardId(Long userId, Long boardId);
}
