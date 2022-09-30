package ru.acme.web.service;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;
import ru.acme.web.model.interfaces.ImageURLs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class ImageServiceTest extends AbstractServiceTest {
    private static final String TEST_PATH = "src/test/resources/";
    @Autowired
    ImageService service;

    @Value("#{'${media.image.resolutions}'.split(',')}")
    private List<Integer> resolutions;

    @BeforeEach
    void setTestImageFolder() {
        ReflectionTestUtils.setField(service, "imageFolder", TEST_PATH);
    }

    @Test
    void get() throws IOException {
        byte[] imgBytes = service.get("testImg.jpeg", "testfolder");
        byte[] bytes;
        try (InputStream inputStream = Files.newInputStream(Path.of(TEST_PATH + "/testfolder/testImg.jpeg"))) {
            bytes = inputStream.readAllBytes();
        }
        Assertions.assertArrayEquals(bytes, imgBytes);
    }

    @Test
    void getWitException() {
        Assertions.assertThrows(FileNotFoundException.class, () ->
                service.get("notExistedFile", null));
    }

    @Test
    void deleteImgFolder() throws IOException {
        Path tempFolder = Path.of(TEST_PATH + "/tempFolder");
        FileUtils.deleteDirectory(tempFolder.toFile());
        Files.createDirectories(tempFolder);
        Path toDelete1 = Files.createFile(tempFolder.resolve("toDelete1.jpeg"));
        Path toDelete2 = Files.createFile(tempFolder.resolve("toDelete2.jpeg"));
        service.deleteImgFolder(List.of(toDelete1.toString(), toDelete2.toString()));
        Assertions.assertFalse(Files.deleteIfExists(tempFolder));
    }

    @Test
    void deleteListAll() throws IOException {
        Path tempFolder1 = Path.of(TEST_PATH + "/tempFolder1");
        FileUtils.deleteDirectory(tempFolder1.toFile());
        Files.createDirectories(tempFolder1);
        Path tempFolder2 = Path.of(TEST_PATH + "/tempFolder2");
        FileUtils.deleteDirectory(tempFolder2.toFile());
        Files.createDirectories(tempFolder2);
        Files.createDirectories(tempFolder2);
        Path toDelete1 = Files.createFile(tempFolder1.resolve("toDelete1.jpeg"));
        Path toDelete2 = Files.createFile(tempFolder2.resolve("toDelete2.jpeg"));
        ImageURLs imageURLs1 = new ImageURLs() {
            @Override
            public List<String> getImgUrl() {
                return List.of(toDelete1.toString());
            }

            @Override
            public void setImgUrl(List<String> imgURLs) {

            }
        };
        ImageURLs imageURLs2 = new ImageURLs() {
            @Override
            public List<String> getImgUrl() {
                return List.of(toDelete2.toString());
            }

            @Override
            public void setImgUrl(List<String> imgURLs) {

            }
        };
        service.deleteListAll(List.of(imageURLs1, imageURLs2));
        Assertions.assertFalse(Files.deleteIfExists(tempFolder1));
        Assertions.assertFalse(Files.deleteIfExists(tempFolder2));
    }

    @Test
    void save() throws IOException {
        File tempFileForSaving = Path.of(TEST_PATH + "saveFolder").toFile();
        FileUtils.deleteDirectory(tempFileForSaving);
        byte[] bytes;
        try (InputStream inputStream = Files.newInputStream(Path.of(TEST_PATH + "/testfolder/testImg.jpeg"))) {
            bytes = inputStream.readAllBytes();
        }
        String base64ImageString = Base64.encodeBase64String(bytes);
        String dataString = "data:image," + base64ImageString;
        List<String> updateFolders = service.save(dataString, "saveFolder");
        Assertions.assertEquals(updateFolders.size(), resolutions.size());
        String[] fileNames = tempFileForSaving.list();
        assert fileNames != null;
        Assertions.assertEquals(fileNames.length, resolutions.size());
        for (Integer resolution : resolutions) {
            Optional<String> optional = Arrays
                    .stream(fileNames)
                    .filter(name -> name.contains(resolution.toString()))
                    .findAny();
            Assertions.assertTrue(optional.isPresent());
        }
        FileUtils.deleteDirectory(tempFileForSaving);
        Assertions.assertFalse(Files.deleteIfExists(tempFileForSaving.toPath()));
    }
}