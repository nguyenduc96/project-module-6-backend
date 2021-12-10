package com.diosa.controller;

import com.diosa.model.project.Project;
import com.diosa.model.project.ProjectResponse;
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
import java.util.*;

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

    @GetMapping("/get-by-project-owner")
    public ResponseEntity<List<Project>> getAllByProjectOwner(Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = new User();
        user.setId(userPrinciple.getId());
        List<Project> projects = projectService.findProjectByProjectOwner(user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable Long id, Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        List<Project> projects = projectService.findProjectByUserId(userPrinciple.getId());
        User user = new User();
        user.setId(userPrinciple.getId());
        List<Project> myProjects = projectService.findProjectByProjectOwner(user);
        for (Project project : projects) {
            if (project.getId().equals(id)) {
                return new ResponseEntity<>(projectService.findProjectById(id), HttpStatus.OK);
            }
        }
        for (Project project : myProjects) {
            if (project.getId().equals(id)) {
                return new ResponseEntity<>(projectService.findProjectById(id), HttpStatus.OK);
            }
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
    public ResponseEntity<Project> update(@PathVariable Long id, @RequestBody Project project, Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = new User();
        user.setId(userPrinciple.getId());
        Optional<Project> projectOptional = projectService.findById(id);
        if (projectOptional.isPresent()) {
            if (projectOptional.get().getProjectOwner().getId().equals(user.getId())) {
                projectOptional.get().setTitle(project.getTitle());
                projectOptional.get().setType(project.getType());
                projectOptional.get().setUpdateAt(convertDateToString(new Date()));
                return new ResponseEntity<>(projectService.save(projectOptional.get()), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}/add-user")
    public ResponseEntity<Project> updateProject(@PathVariable Long id,@RequestBody User user, Authentication authentication) {
        Optional<User> userOptional = userService.findByEmail(user.getEmail());
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Optional<Project> projectOptional = projectService.findById(id);
        if (!projectOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<User> usersInProject = userService.findUsersByProjectId(id);
        for (User userInProject : usersInProject) {
            if (userInProject.getEmail().equals(user.getEmail())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        User user1 = new User();
        user1.setId(userPrinciple.getId());
        List<Project> projects = projectService.findProjectByProjectOwner(user1);
        for (Project project : projects) {
            if (project.getId().equals(id)) {
                Set<User> users = new HashSet<>();
                users.add(userOptional.get());
                users.addAll(usersInProject);
                projectOptional.get().setUsers(users);
                Project p = projectService.save(projectOptional.get());
                return new ResponseEntity<>(p, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = new User();
        user.setId(userPrinciple.getId());
        List<Project> projects = projectService.findProjectByProjectOwner(user);
        for (Project project : projects) {
            if (project.getId().equals(id)) {
                projectService.remove(id);
                projects = projectService.findProjectByProjectOwner(user);
                return new ResponseEntity<>(projects, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{projectId}/get_user")
    public ResponseEntity<Iterable<User>> getUserByProjectId(@PathVariable Long projectId) {
        Optional<Project> projectOptional = projectService.findById(projectId);
        if (!projectOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userService.findUsersByProjectId(projectId), HttpStatus.OK);

    }
}
