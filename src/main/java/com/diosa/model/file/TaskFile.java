package com.diosa.model.file;

import com.diosa.model.Common;
import com.diosa.model.task.Task;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class TaskFile extends Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Task task;
}
