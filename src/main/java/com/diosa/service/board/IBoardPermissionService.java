package com.diosa.service.board;

import com.diosa.model.permission.BoardPermission;
import com.diosa.service.IBaseService;

public interface IBoardPermissionService extends IBaseService<BoardPermission> {
    BoardPermission findByUserIdAndBoardId(Long userId, Long boardId);

    void deleteByUserIdAndBoardId(Long userId, Long boardId);

}
