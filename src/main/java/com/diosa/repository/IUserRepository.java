package com.diosa.repository;

import com.diosa.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    @Transactional
    Optional<User> findByUsername(String username);
}
