package com.diosa.service.status;

import com.diosa.model.status.Status;
import com.diosa.model.status.StatusResponse;
import com.diosa.service.IBaseService;

import java.util.List;

public interface IStatusService extends IBaseService<Status> {

    List<Status> findAllByBoardIdOrderByPositionAsc(Long id);

    List<StatusResponse> findByBoardId(Long id);

    void deleteAllByBoardId(Long boardId);
}
