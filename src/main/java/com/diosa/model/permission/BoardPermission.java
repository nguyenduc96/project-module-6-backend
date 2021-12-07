package com.diosa.model.permission;

import com.diosa.model.board.Board;
import com.diosa.model.user.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class BoardPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Board board;

    @ManyToOne
    private Permission permission;
}
