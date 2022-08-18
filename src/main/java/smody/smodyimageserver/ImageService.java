package smody.smodyimageserver;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    public static final String IMAGE_FOLDER_LOCATION = "./images/";

    public String uploadImage(MultipartFile rawImage) {
        String imageFullName = generateImageFullName(rawImage);
        saveImageFile(rawImage, imageFullName);
        return "https://images.smody.co.kr/images/" + imageFullName;
    }

    private void makeImageFolder() {
        File imageFolder = new File(IMAGE_FOLDER_LOCATION);
        if (!imageFolder.exists()) {
            imageFolder.mkdir();
        }
    }

    private String generateImageFullName(MultipartFile rawImage) {
        String randomFileName = UUID.randomUUID().toString();
        String imageType = rawImage.getOriginalFilename().split("\\.")[1];
        return randomFileName + "." + imageType;
    }

    private void saveImageFile(MultipartFile rawImage, String imageFullName) {
        try {
            FileCopyUtils.copy(rawImage.getBytes(), new File(IMAGE_FOLDER_LOCATION + imageFullName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
