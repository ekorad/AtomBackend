package com.atom.application.services;

import java.util.List;
import java.util.stream.Collectors;

import com.atom.application.dtos.WebUserDTO;
import com.atom.application.mappers.WebUserMapper;
import com.atom.application.models.WebUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class WebUserFacade {

    @Autowired
    private WebUserService service;
    @Autowired
    private WebUserMapper mapper;

    public List<WebUserDTO> getAllWebUsers() {
        List<WebUser> entities = service.getAllWebUsers();
        return entities.stream().map(mapper::mapToDto).collect(Collectors.toList());
    }

    public WebUserDTO getWebUserByUsername(String username) {
        WebUser entity = service.getWebUserByUsername(username);
        return mapper.mapToDto(entity);
    }

    public WebUserDTO getWebUserByEmail(String email) {
        WebUser entity = service.getWebUserByEmail(email);
        return mapper.mapToDto(entity);
    }

    public void addNewWebUser(WebUserDTO dto) {
        WebUser entity = mapper.mapToEntity(dto);
        service.addNewWebUser(entity);
    }

    public void deleteWebUsersByUsernames(List<String> usernames) {
        List<WebUser> entities = service.getAllWebUsersByUsernames(usernames);
        service.removeWebUsers(entities);
    }

    public void updateExistingWebUser(String existingUsername, WebUserDTO editedWebUserDTO) {
        WebUser entity = mapper.mapToEntity(editedWebUserDTO);
        service.updateWebUser(existingUsername, entity);
    }

    public WebUserDTO getWebUserByUsernameSelf(String username) {
        String currentUserUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!currentUserUsername.equals(username)) {
            throw new IllegalArgumentException("Requested user is not currently logged in");
        }
        WebUserDTO dto = getWebUserByUsername(username);
        dto.setPassword("PROTECTED");
        return dto;
    }

}
