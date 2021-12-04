package com.diosa.service.board;

import com.diosa.model.board.Board;
import com.diosa.model.board.BoardResponse;
import com.diosa.service.IBaseService;

public interface IBoardService extends IBaseService<Board> {
    BoardResponse findBoardById(Long boardId);
}
