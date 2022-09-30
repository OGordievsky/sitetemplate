package ru.acme.web.service;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.acme.web.model.interfaces.ImageURLs;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {
    @Value("${media.image.path}")
    private String imageFolder;
    @Value("#{'${media.image.resolutions}'.split(',')}")
    private List<Integer> resolutions;

    public List<String> save(String imageData, String folder) {
        List<String> resultImgURL = new ArrayList<>();
        if (imageData != null && imageData.startsWith("data:image")) {
            String[] imgData = imageData.split(",");
            String extension = switch (imgData[0]) {
                case "data:image/jpeg;base64" -> ".jpeg";
                case "data:image/png;base64" -> ".png";
                default -> ".jpg";
            };
            byte[] data = Base64.decodeBase64(imgData[1]);

            File newImgDir = Paths.get(imageFolder, folder).toFile();
            if (newImgDir.exists() || newImgDir.mkdirs()) {
                for (Integer resolution : resolutions) {
                    try (InputStream stream = new ByteArrayInputStream(data)) {
                        String imgName = resolution + "w" + extension;
                        Thumbnails.of(stream)
                                .size(resolution, resolution)
                                .toFile(Paths.get(imageFolder, folder, imgName).toFile());
                        resultImgURL.add("/img/" + folder + "/" + imgName);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return resultImgURL;
    }

    public <V extends ImageURLs> void deleteListAll(List<? extends ImageURLs> objects) throws IOException {
        for (ImageURLs old : objects) {
            if (old != null) {
                deleteImgFolder(old.getImgUrl());
            }
        }
    }

    public void deleteImgFolder(List<String> imageList) throws IOException {
        if (imageList != null && !imageList.isEmpty()) {
            String oldDir = Paths.get(imageList.get(0)).getParent().toFile().getName();
            if (!oldDir.startsWith("0")) {
                File file = Paths.get(imageFolder, oldDir).toFile();
                FileUtils.deleteDirectory(file);
            }
        }
    }

    public byte[] get(String imgName, String imgFolder) throws IOException {
        Path url = imgFolder != null ? Paths.get(imageFolder, imgFolder, imgName) : Paths.get(imageFolder, imgName);
        try (InputStream in = new FileInputStream(url.toFile())) {
            return IOUtils.toByteArray(in);
        }
    }
}
