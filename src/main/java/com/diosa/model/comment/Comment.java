package com.diosa.model.comment;

import com.diosa.model.Common;
import com.diosa.model.task.Task;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Comment extends Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    private Task task;
}
