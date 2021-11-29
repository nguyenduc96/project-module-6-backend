package com.diosa.model.group;

import com.diosa.model.Common;
import com.diosa.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Group extends Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int type;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> users;
}
