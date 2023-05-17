package com.atom.application.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.atom.application.dtos.UserRoleDTO;
import com.atom.application.services.UserRoleFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/users/roles")
public class UserRoleController {

    @Autowired
    private UserRoleFacade service;

    @GetMapping
    public List<UserRoleDTO> getAllUserRoles() {
        return service.getAllUserRoles();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public void addNewUserRole(@Valid @RequestBody UserRoleDTO newUserRoleDTO) {
        service.addNewUserRole(newUserRoleDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "/update", params = {"name"})
    public void updateExistingUserRole(@RequestParam @NotBlank(message = "User role name is mandatory and must not contain only whitespace")
        @Size(min = 4, max = 50, message = "User role name must contain between 4 and 50 characters") String name,
        @Valid @RequestBody UserRoleDTO editedUserRoleDTO) {
        service.updateExistingUserRole(name, editedUserRoleDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/remove", params = {"names"})
    public void deleteUserRoles(@RequestParam @NotEmpty(message = "List of user roles to be deleted is mandatory and cannot be empty") 
        List<@NotBlank(message = "User role name is mandatory and must not contain only whitespace")
        @Size(min = 4, max = 50, message = "User role name must contain between 4 and 50 characters") String> names) {
        service.deleteUserRolesByName(names);
    }

}
