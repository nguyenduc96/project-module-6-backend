package com.diosa.model.permission;

import com.diosa.model.project.Project;
import com.diosa.model.user.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ProjectPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Permission permission;
}
