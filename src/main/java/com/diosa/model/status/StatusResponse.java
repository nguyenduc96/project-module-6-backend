package com.diosa.model.status;

import com.diosa.model.task.Task;
import lombok.Data;

import java.util.List;

@Data
public class StatusResponse {
    private Long id;

    private String title;

    private int position;

    private List<Task> tasks;
}
