package com.diosa.repository;

import com.diosa.model.permission.BoardPermission;
import com.diosa.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBoardPermissionRepository extends JpaRepository<BoardPermission, Long> {
}
