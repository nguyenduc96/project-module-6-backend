package com.diosa.service.comment;

import com.diosa.model.comment.Comment;
import com.diosa.service.IBaseService;

import java.util.List;

public interface ICommentService extends IBaseService<Comment> {
    List<Comment> findAllByTaskId(Long TaskId);

    void deleteByTaskId(Long id);
}



