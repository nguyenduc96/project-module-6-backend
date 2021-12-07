package com.diosa.repository;

import com.diosa.model.permission.BoardPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBoardPermissionRepository extends JpaRepository<BoardPermission, Long> {
}
