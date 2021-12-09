package com.diosa.model.notification;

import com.diosa.model.board.Board;
import com.diosa.model.user.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    private String action;

    private String date;

    @ManyToOne
    private User receiver;

    private boolean status;

    private String link;
}
