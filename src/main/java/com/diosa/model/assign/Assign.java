package com.diosa.model.assign;

import com.diosa.model.Common;
import com.diosa.model.task.Task;
import com.diosa.model.user.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Assign extends Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Task task;

    @ManyToOne
    private User user;
}
