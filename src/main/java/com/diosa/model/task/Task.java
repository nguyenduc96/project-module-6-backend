package com.diosa.model.task;

import com.diosa.model.Common;
import com.diosa.model.label.Label;
import com.diosa.model.status.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
    private List<Label> labels;
}
