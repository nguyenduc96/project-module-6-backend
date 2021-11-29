package com.diosa.model.board;

import com.diosa.model.Common;
import com.diosa.model.project.Project;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Board extends Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    private Project project;
}


