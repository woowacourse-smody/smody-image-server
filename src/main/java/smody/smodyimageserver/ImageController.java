package smody.smodyimageserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

    @Value("${secret.key.upload}")
    private String secretKeyUpload;

    private ImageService imageService;

    public ImageController(final ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/images/upload")
    public ResponseEntity<String> uploadImage(@RequestPart MultipartFile rawImage, @RequestParam String secretKeyUpload) {
        if (!this.secretKeyUpload.equals(secretKeyUpload)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String url = imageService.uploadImage(rawImage);
        return ResponseEntity.ok(url);
    }
}
