package com.diosa.service.comment;

import com.diosa.model.comment.Comment;
import com.diosa.model.task.Task;
import com.diosa.service.IBaseService;

import java.util.List;

public interface ICommentService extends IBaseService<Comment> {

    void deleteByTaskId(Long id);
}



