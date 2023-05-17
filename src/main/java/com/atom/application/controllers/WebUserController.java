package com.atom.application.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.atom.application.dtos.WebUserDTO;
import com.atom.application.models.WebUser;
import com.atom.application.services.PasswordResetRequestService;
import com.atom.application.services.WebUserFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
public class WebUserController {

    @Autowired
    private WebUserFacade service;
    @Autowired
    private PasswordResetRequestService passResetService;

    @GetMapping
    public List<WebUserDTO> getAllWebUsers() {
        return service.getAllWebUsers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public void addNewWebUser(@Valid @RequestBody WebUserDTO newWebUserDTO) {
        service.addNewWebUser(newWebUserDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/remove", params = { "usernames" })
    public void deleteWebUsers(
            @RequestParam @NotEmpty(message = "List of user accounts to be deleted is mandatory and cannot be empty") List<@NotBlank(message = "User account username is mandatory and must not contain only whitespace") @Size(min = 5, max = 50, message = "User account username must contain between 5 and 30 characters") String> usernames) {
        service.deleteWebUsersByUsernames(usernames);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "/update", params = { "username" })
    public void updateExistingWebUser(
            @RequestParam @NotBlank(message = "User username is mandatory and cannot contain only whitespace") @Size(min = 5, max = 30, message = "User username must contain between 5 and 30 valid characters") String username,
            @Valid @RequestBody WebUserDTO editedWebUserDTO) {
        service.updateExistingWebUser(username, editedWebUserDTO);
    }

    @GetMapping(path = "/check", params = { "username" })
    public Boolean checkIfUsernameExists(
            @RequestParam @NotBlank(message = "User username is mandatory and cannot contain only whitespace") @Size(min = 5, max = 30, message = "User username must contain between 5 and 30 valid characters") String username) {
        Boolean exists = false;
        try {
            WebUserDTO user = service.getWebUserByUsername(username);
            exists = true;
        } catch (EntityNotFoundException ex) {
            exists = false;
        }
        return exists;
    }

    @GetMapping(path = "/check", params = { "email" })
    public Boolean checkIfEmailExists(
            @RequestParam @NotBlank(message = "User email is mandatory and cannot contain only whitespace") @Size(min = 5, max = 70, message = "User email must contain between 5 and 70 valid characters") String email) {
        Boolean exists = false;
        try {
            WebUserDTO user = service.getWebUserByEmail(email);
            exists = true;
        } catch (EntityNotFoundException ex) {
            exists = false;
        }
        return exists;
    }

    @GetMapping(params = { "username" })
    public WebUserDTO getUserSelf(
            @NotBlank(message = "Requested user username is mandatory and cannot contain only whitespace") @Size(min = 5, max = 30, message = "Requested user username must contain between 5 and 30 valid characters") @RequestParam String username) {
        return service.getWebUserByUsernameSelf(username);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/pass-reset-request", params = { "username" })
    public void requestResetByUsername(@RequestParam String username) {
        try {
            passResetService.requestPasswordResetByUsername(username);
        } catch (NoSuchAlgorithmException exc) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "No such algorithm found for message digest", exc);
        }
    }
}
