package com.diosa.service.board;

import com.diosa.model.board.Board;
import com.diosa.model.board.BoardResponse;
import com.diosa.model.status.StatusResponse;
import com.diosa.repository.IBoardRepository;
import com.diosa.service.label.ILabelService;
import com.diosa.service.status.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BoardService implements IBoardService{

    @Autowired
    private IBoardRepository boardRepository;

    @Autowired
    private IStatusService statusService;

    @Autowired
    private ILabelService labelService;

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
        labelService.deleteAllByBoardId(id);
        statusService.deleteAllByBoardId(id);
        boardRepository.deleteById(id);
    }

    @Override
    public List<Board> findAllByProjectId(Long projectId) {
        return boardRepository.findAllByProjectId(projectId);
    }

    @Override
    public BoardResponse findBoardById(Long boardId) {
        Board board =  boardRepository.findById(boardId).get();
        return convertToBoardResponse(board);
    }

    private BoardResponse convertToBoardResponse(Board board) {
        List<StatusResponse> statusResponses = statusService.findByBoardId(board.getId());
        Set<StatusResponse> statusResponseSet = new HashSet<>();
        for(StatusResponse statusResponse : statusResponses) {
            statusResponseSet.add(statusResponse);
        }
        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setId(board.getId());
        boardResponse.setTitle(board.getTitle());
        boardResponse.setProject(board.getProject().getId());
        boardResponse.setStatuses(statusResponseSet);
        return boardResponse;
    }
}
