package com.diosa.service.board;

import com.diosa.model.permission.BoardPermission;
import com.diosa.repository.IBoardPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardPermissionService implements IBoardPermissionService{
    @Autowired
    private IBoardPermissionRepository boardPermissionRepository;

    @Override
    public Iterable<BoardPermission> findAll() {
        return boardPermissionRepository.findAll();
    }

    @Override
    public Optional<BoardPermission> findById(Long id) {
        return boardPermissionRepository.findById(id);
    }

    @Override
    public BoardPermission save(BoardPermission boardPermission) {
        return boardPermissionRepository.save(boardPermission);
    }

    @Override
    public void remove(Long id) {
        boardPermissionRepository.deleteById(id);
    }

    @Override
    public BoardPermission findByUserIdAndBoardId(Long userId, Long boardId) {
        return boardPermissionRepository.findByUserIdAndBoardId(userId, boardId);
    }

    @Override
    public void deleteByUserIdAndBoardId(Long userId, Long boardId) {
        boardPermissionRepository.deleteByUserIdAndBoardId(userId, boardId);
    }
}
