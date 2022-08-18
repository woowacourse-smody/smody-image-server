package smody.smodyimageserver;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://192.168.2.140:8080")
public class ImageController {

    private ImageService imageService;

    public ImageController(final ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/images/upload")
    public ResponseEntity<String> uploadImage(@RequestPart MultipartFile rawImage) {
        String url = imageService.uploadImage(rawImage);
        return ResponseEntity.ok(url);
    }
}
