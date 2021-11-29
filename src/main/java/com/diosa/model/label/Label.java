package com.diosa.model.label;

import com.diosa.model.color.Color;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @OneToOne
    private Color color;
}
