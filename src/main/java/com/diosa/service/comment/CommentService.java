package com.diosa.service.comment;

import com.diosa.model.comment.Comment;
import com.diosa.repository.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private ICommentRepository commentRepository;

    @Override
    public Iterable<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void remove(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void deleteByTaskId(Long id) {
        commentRepository.deleteAllByTaskId(id);
    }

    @Override
    public List<Comment> findAllByTaskId(Long taskId) {
        return commentRepository.findAllByTaskId(taskId);
    }
}
