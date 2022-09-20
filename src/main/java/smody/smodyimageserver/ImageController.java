package smody.smodyimageserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<String> uploadImage(@RequestPart MultipartFile rawImage,
                                              @RequestPart String secretKeyUpload) {
        if (!this.secretKeyUpload.equals(secretKeyUpload)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String url = imageService.uploadImage(rawImage);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/images/{name}")
    public ResponseEntity<FileSystemResource> getImage(@PathVariable String name) throws IOException {
        Path path = new File("/home/ubuntu/images/" + name).toPath();
//        Path path = new File("/Users/jojogreen/Desktop/" + name).toPath();
        FileSystemResource resource = new FileSystemResource(path);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(Files.probeContentType(path)))
                .header("Access-Control-Allow-Origin", "*")
                .body(resource);
    }
}
