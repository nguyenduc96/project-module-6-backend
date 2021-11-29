package com.diosa.model.status;

import com.diosa.model.Common;
import com.diosa.model.board.Board;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Status extends Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int position;

    @ManyToOne
    private Board board;
}
