package com.atom.application.controllers;

import java.util.List;

import com.atom.application.dtos.UserPermissionDTO;
import com.atom.application.services.UserPermissionFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>Rest controller for user permissions</b>
 * <p>
 * This is a REST controller which maps facade
 * (<code>UserPermissionFacade</code>) functionality to APIs. This means that
 * the controller only deals with data transfer objects, and not entities.
 * <p>
 * In the case of user permissions, only the "get-all" functionality is required
 * to be exposed by the controller.
 * 
 * @see {@link com.atom.application.services.UserPermissionFacade
 *      UserPermissionFacade}
 * @see {@link com.atom.application.dtos.UserPermissionDTO UserPermissionDTO}
 */
@RestController
@RequestMapping("/users/permissions")
@Validated
public class UserPermissionController {

    /**
     * The facade service used for retrieving the user permission DTOs.
     */
    @Autowired
    private UserPermissionFacade service;

    /**
     * Returns a list of all existing user permissions
     * @return a <code>List</code> of all existing user permissions conveniently mapped as DTOs (<code>UserPermissionDTO</code>)
     */
    @GetMapping
    public List<UserPermissionDTO> getAllPermissions() {
        return service.getAllUserPermissions();
    }

}
