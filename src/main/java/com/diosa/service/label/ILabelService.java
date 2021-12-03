package com.diosa.service.label;

import com.diosa.model.label.Label;
import com.diosa.service.IBaseService;

import java.util.List;

public interface ILabelService extends IBaseService<Label> {
    List<Label> findAllByBoardId(Long id);

    void deleteAllByBoardId(Long id);
}
