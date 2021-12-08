package com.diosa.repository;

import com.diosa.model.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {



    List<Comment> findAllByTaskId(Long id);

    @Modifying
    @Transactional
    void deleteAllByTaskId(Long id);
}
