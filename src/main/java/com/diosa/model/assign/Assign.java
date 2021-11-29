package com.diosa.model.assign;

import com.diosa.model.Common;
import com.diosa.model.task.Task;
import com.diosa.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assign extends Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Task task;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> users;
}
