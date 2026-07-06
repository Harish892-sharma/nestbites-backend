package homies.com.backend.controller.upload;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import homies.com.backend.dto.upload.UploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private Cloudinary cloudinary;

    @PostMapping
    public ResponseEntity<UploadResponse> uploadImage(
            @RequestParam("file") MultipartFile file) {

        UploadResponse response = new UploadResponse();

        try {

            Map<?, ?> uploadResult =
                    cloudinary.uploader().upload(
                            file.getBytes(),
                            ObjectUtils.emptyMap()
                    );

            response.setSuccess(true);
            response.setImageUrl(uploadResult.get("secure_url").toString());
            response.setPublicId(uploadResult.get("public_id").toString());
            response.setMessage("Image uploaded successfully");

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.setSuccess(false);
            response.setMessage(e.getMessage());

            return ResponseEntity.badRequest().body(response);
        }
    }
}