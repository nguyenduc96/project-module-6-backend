package com.diosa.service.board;

import com.diosa.model.board.Board;
import com.diosa.repository.IBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardService implements IBoardService{

    @Autowired
    private IBoardRepository boardRepository;

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
}
