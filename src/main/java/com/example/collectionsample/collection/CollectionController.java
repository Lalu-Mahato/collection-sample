package com.example.collectionsample.collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/collection")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @GetMapping("/target-collections")
    public ResponseEntity<Object> getCollectionTarget(@RequestParam String date) {
        try {
            ResponseEntity<Object> response = collectionService.findCollectionTarget(date);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error:" + e.getMessage());
        }
    }

    @PostMapping("/upload-collection")
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile multipartFile) {
        try {
            ResponseEntity<Object> response = collectionService.uploadCollections(multipartFile);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error:" + e.getMessage());
        }
    }
}
