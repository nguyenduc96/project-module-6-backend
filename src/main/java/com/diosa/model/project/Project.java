package com.diosa.model.project;

import com.diosa.model.Common;
import com.diosa.model.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Project extends Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Long type;

    @ManyToOne
    private User projectOwner;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> users;
}
