package com.diosa.model.task;

import com.diosa.model.Common;
import com.diosa.model.label.Label;
import com.diosa.model.status.Status;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Task extends Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String deadline;

    private String image;

    private int position;

    @ManyToOne
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Label> labels;
}
