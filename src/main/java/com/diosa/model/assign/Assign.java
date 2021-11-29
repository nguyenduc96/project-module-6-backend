package com.diosa.model.assign;

import com.diosa.model.Common;
import com.diosa.model.task.Task;
import com.diosa.model.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Assign extends Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Task task;

    @ManyToMany
    private List<User> users;
}
