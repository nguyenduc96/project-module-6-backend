package com.diosa.controller;

import com.diosa.model.label.Label;
import com.diosa.service.label.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/labels")
public class LabelController {
    @Autowired
    private ILabelService labelService;

    @GetMapping("/{boardId}")
    public ResponseEntity<List<Label>> getAllByBoardId(@PathVariable Long boardId) {
        return new ResponseEntity<>(this.labelService.findAllByBoardId(boardId), HttpStatus.OK) ;
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Label> getById(@PathVariable Long id) {
        Optional<Label> labelOptional = labelService.findById(id);
        if (!labelOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(labelService.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Label> addNewLabel(@RequestBody Label label) {
        return new ResponseEntity<>(this.labelService.save(label), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Label> editNewLabel(@RequestBody Label label, @PathVariable Long id) {
        Optional<Label> labelOptional = labelService.findById(id);
        if (!labelOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(labelService.save(label), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLabel( @PathVariable Long id) {
        Optional<Label> labelOptional = labelService.findById(id);
        if (!labelOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        labelService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
