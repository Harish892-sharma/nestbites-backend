package homies.com.backend.controller;

import homies.com.backend.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(
            @RequestParam("file") MultipartFile file
    ) {

        String imageUrl = cloudinaryService.uploadImage(file);

        Map<String, String> response = new HashMap<>();

        response.put("imageUrl", imageUrl);

        return ResponseEntity.ok(response);
    }
}