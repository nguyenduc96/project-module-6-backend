package com.diosa.repository;

import com.diosa.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    @Transactional
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM user u JOIN project_users pu ON u.id = pu.users_id WHERE pu.project_id = ?1", nativeQuery = true)
    List<User> findUsersByProjectId(Long projectId);
}
