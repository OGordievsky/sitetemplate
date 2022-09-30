package ru.acme.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.acme.web.service.ImageService;

import java.io.IOException;


@RestController
@RequestMapping("/img")
public class ImageController {

    ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = {"/{imgName}", "/{imgFolder}/{imgName}"}, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(
            @PathVariable(value = "imgFolder", required = false) String imgFolder,
            @PathVariable(value = "imgName") String imgName
    ) {
        try {
            return new ResponseEntity<>(imageService.get(imgName, imgFolder), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
