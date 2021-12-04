package com.diosa.controller;

import com.diosa.model.project.Project;
import com.diosa.model.user.User;
import com.diosa.model.user.UserPrinciple;
import com.diosa.service.project.IProjectService;
import com.diosa.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<Iterable<Project>> getAll() {
        return new ResponseEntity<>(projectService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get-by-user")
    public ResponseEntity<List<Project>> getAllByUserId(Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        List<Project> projects = projectService.findProjectByUserId(userPrinciple.getId());
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id, Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Optional<Project> projectOptional = projectService.findById(id);
        if (projectService.findProjectByUserId(userPrinciple.getId()).contains(projectOptional.get())) {
            return new ResponseEntity<>(projectOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Project> editProject(@RequestBody Project project, Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = new User();
        user.setId(userPrinciple.getId());
        project.setProjectOwner(user);
        project.setCreateAt(convertDateToString(new Date()));
        return new ResponseEntity<>(projectService.save(project), HttpStatus.OK);
    }

    private String convertDateToString(Date date) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> update(@PathVariable Long id, @RequestBody Project project) {
        Optional<Project> projectOptional = projectService.findById(id);
        if (projectOptional.isPresent()) {
            projectOptional.get().setTitle(project.getTitle());
            projectOptional.get().setType(project.getType());
            projectOptional.get().setUpdateAt(convertDateToString(new Date()));
            return new ResponseEntity<>(projectService.save(projectOptional.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}/add-user")
    public ResponseEntity<Project> updateProject(@PathVariable Long id,@RequestBody User user, Authentication authentication) {
        if (!userService.findByUsername(user.getUsername()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Optional<Project> projectOptional = projectService.findById(id);
        if (!projectOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (projectService.findProjectByUserId(userPrinciple.getId()).contains(projectOptional.get())) {
            List<User> users = new ArrayList<>();
            users.add(user);
            projectOptional.get().setUsers(users);
            return new ResponseEntity<>(projectService.save(projectOptional.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Project> delete(@PathVariable Long id) {
        Optional<Project> projectOptional = projectService.findById(id);
        if (!projectOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        projectService.remove(id);
        return new ResponseEntity<>(projectOptional.get(), HttpStatus.OK);
    }
}
