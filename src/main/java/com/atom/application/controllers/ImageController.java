package com.atom.application.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.atom.application.services.FileService;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/products/image")
public class ImageController {

    @Autowired
    private FileService service;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE, params = { "id" })
    public void getProductImageById(HttpServletResponse response, @RequestParam Long id) throws IOException {
        try {
            final String path = "img/pid" + id + "/image.jpg";
            ClassPathResource img = new ClassPathResource(path);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(img.getInputStream(), response.getOutputStream());
        } catch (FileNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not find an image for product id: " + id);
        } catch (IOException exc) {
            throw exc;
        }
    }

    /*
     * @PostMapping(path = "/upload", params = { "id", "file" }) public void
     * uploadImageForId(@RequestParam Long id, @RequestParam MultipartFile file) {
     * try { service.uploadFile(file, id); } catch (IOException exc) { throw new
     * ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
     * "Could not upload image file"); } }
     */

    @PostMapping("/upload")
    public void uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
        try {
            service.uploadFile(file, id);
        } catch (IOException exc) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not upload image file", exc);
        }
    }

}
