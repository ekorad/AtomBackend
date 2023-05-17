package com.atom.application.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.atom.application.models.PasswordResetRequest;
import com.atom.application.models.WebUser;
import com.atom.application.repos.PasswordResetRequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetRequestService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private PasswordResetRequestRepository passResetRequestRepo;
    @Autowired
    private WebUserService userService;

    public PasswordResetRequest getRequestByWebUserUsername(String username) {
        WebUser requestedUser = userService.getWebUserByUsername(username);
        Optional<PasswordResetRequest> requestOpt = passResetRequestRepo.findByUserId(requestedUser.getId());
        if (!requestOpt.isPresent()) {
            throw new EntityNotFoundException(
                    "Could not find password reset request for user with username: '" + username + "'");
        }
        PasswordResetRequest request = requestOpt.get();
        return request;
    }

    public PasswordResetRequest getRequestByWebUserEmail(String userEmail) {
        WebUser requestedUser = userService.getWebUserByEmail(userEmail);
        Optional<PasswordResetRequest> requestOpt = passResetRequestRepo.findByUserId(requestedUser.getId());
        if (!requestOpt.isPresent()) {
            throw new EntityNotFoundException(
                    "Could not find password reset request for user with email: '" + userEmail + "'");
        }
        PasswordResetRequest request = requestOpt.get();
        return request;
    }

    public PasswordResetRequest requestPasswordResetByUsername(String username) throws NoSuchAlgorithmException {
        WebUser requestedUser = userService.getWebUserByUsername(username);
        return requestResetForUser(requestedUser);
    }

    public PasswordResetRequest requestPasswordResetByEmail(String userEmail) throws NoSuchAlgorithmException {
        WebUser requestedUser = userService.getWebUserByEmail(userEmail);
        return requestResetForUser(requestedUser);
    }

    private PasswordResetRequest requestResetForUser(WebUser requestedUser) throws NoSuchAlgorithmException {
        Optional<PasswordResetRequest> existingRequestOpt = passResetRequestRepo.findByUserId(requestedUser.getId());
        if (existingRequestOpt.isPresent()) {
            passResetRequestRepo.delete(existingRequestOpt.get());
        }

        PasswordResetRequest request = new PasswordResetRequest();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String originalString = requestedUser.getUsername() + System.currentTimeMillis() + requestedUser.getId();
        byte[] encodedHash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        String token = bytesToHex(encodedHash);
        request.setUser(requestedUser);
        request.setToken(token);

        String messageString = "Acceseaza link-ul urmator pentru a iti putea reseta parola: ";
        messageString += "http://192.168.1.194:4200/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("atom.shop.office@gmail.com");
        message.setTo(requestedUser.getEmail());
        message.setSubject("Resetare parola");
        message.setText(messageString);
        mailSender.send(message);

        return passResetRequestRepo.save(request);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
