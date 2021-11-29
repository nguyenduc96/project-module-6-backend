package com.diosa.repository;

import com.diosa.model.assign.Assign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAssignRepository extends JpaRepository<Assign, Long> {
}
