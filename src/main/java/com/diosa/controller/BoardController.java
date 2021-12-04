package com.diosa.controller;

import com.diosa.model.board.Board;
import com.diosa.model.board.BoardResponse;
import com.diosa.service.board.IBoardService;
import com.diosa.service.project.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/boards")
public class BoardController {

    @Autowired
    private IBoardService boardService;

    @GetMapping
    public ResponseEntity<Iterable<Board>> getAll() {
        return new ResponseEntity<>(boardService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable Long id) {
        Optional<Board> boardOptional = boardService.findById(id);
        if(!boardOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(boardService.findBoardById(id),  HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody Board board) {
        return new ResponseEntity<>(boardService.save(board), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Board> editBoard(@PathVariable Long id, @RequestBody Board board) {
        Optional<Board> boardOptional = boardService.findById(id);
        if (!boardOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        board.setId(id);
        return new ResponseEntity<>(boardService.save(board),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Board> delete(@PathVariable Long id) {
        Optional<Board> boardOptional = boardService.findById(id);
        if (!boardOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boardService.remove(id);
        return new ResponseEntity<>(boardOptional.get(), HttpStatus.OK);
    }
}