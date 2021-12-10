package com.diosa.controller;

import com.diosa.model.label.Label;
import com.diosa.model.task.Task;
import com.diosa.service.label.ILabelService;
import com.diosa.service.task.ITaskService;
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

    @Autowired
    private ITaskService taskService;

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
        if (label.getColor().getId() == null) {
            label.getColor().setId(1L);
        }
        return new ResponseEntity<>(this.labelService.save(label), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Label> editNewLabel(@RequestBody Label label, @PathVariable Long id) {
        Optional<Label> labelOptional = labelService.findById(id);
        if (label.getColor().getId() == null) {
            label.getColor().setId(labelOptional.get().getColor().getId());
        }
        if (label.getColor().getId() == null) {
            label.getColor().setId(1L);
        }if (label.getContent()  == null) {
            label.setContent(labelOptional.get().getContent());
        }
        if (!labelOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        label.setId(id);
        return new ResponseEntity<>(labelService.save(label), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLabel( @PathVariable Long id) {
        Optional<Label> labelOptional = labelService.findById(id);
        if (!labelOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Iterable<Task> tasks = taskService.findAll();
        for (Task task : tasks) {
            task.getLabels().removeIf(label -> label.getId().equals(id));
        }
        labelService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
