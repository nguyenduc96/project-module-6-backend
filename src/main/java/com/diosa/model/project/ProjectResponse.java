package com.diosa.model.project;

import com.diosa.model.board.Board;
import com.diosa.model.user.User;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ProjectResponse {
    private Long id;

    private String title;

    private Long type;

    private User projectOwner;

    private Set<User> users;

    private List<Board> boards;
}
