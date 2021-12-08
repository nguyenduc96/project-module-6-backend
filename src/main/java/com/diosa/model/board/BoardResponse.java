package com.diosa.model.board;

import com.diosa.model.status.StatusResponse;
import lombok.Data;

import java.util.Set;


@Data
public class BoardResponse {
    private Long id;

    private String title;

    private Long project;

    private Set<StatusResponse> statuses;
}
