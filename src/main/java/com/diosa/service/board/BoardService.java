package com.diosa.service.board;

import com.diosa.model.board.Board;
import com.diosa.model.board.BoardResponse;
import com.diosa.repository.IBoardRepository;
import com.diosa.service.status.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardService implements IBoardService{

    @Autowired
    private IBoardRepository boardRepository;

    @Autowired
    private IStatusService statusService;

    @Override
    public Iterable<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    @Override
    public Board save(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public void remove(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public BoardResponse findBoardById(Long boardId) {
        Board board =  boardRepository.findById(boardId).get();
        return convertToBoardResponse(board);
    }

    private BoardResponse convertToBoardResponse(Board board) {
        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setId(board.getId());
        boardResponse.setTitle(board.getTitle());
        boardResponse.setProject(board.getProject());
        boardResponse.setStatuses(statusService.findByBoardId(board.getId()));
        return boardResponse;
    }
}
