package com.diosa.controller;

import com.diosa.model.color.Color;
import com.diosa.service.color.IColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/colors")
public class ColorController {
    @Autowired
    private IColorService colorService;

    @GetMapping
    public ResponseEntity<Iterable<Color>> getAllColor() {
        return new ResponseEntity<>(colorService.findAll(), HttpStatus.OK);
    }
}
