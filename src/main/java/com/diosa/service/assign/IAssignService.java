package com.diosa.service.assign;

import com.diosa.model.assign.Assign;
import com.diosa.service.IBaseService;

public interface IAssignService extends IBaseService<Assign> {
    void deleteAssign(Long userId, Long taskId);

}
