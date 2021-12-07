package com.diosa.service.board;

import com.diosa.model.board.Board;
import com.diosa.model.board.BoardResponse;
import com.diosa.service.IBaseService;

import java.util.List;

public interface IBoardService extends IBaseService<Board> {
    BoardResponse findBoardById(Long boardId);

    List<Board> findAllByProjectId(Long projectId);

    BoardResponse findByBoardIdAndTitleTask(Long boardId, String title);
}
