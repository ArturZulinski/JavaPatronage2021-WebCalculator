package com.patronage.calculator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class HistoryService {

    Logger logger = LoggerFactory.getLogger(HistoryService.class);

    public ResponseEntity downloadFileFromLocal(@RequestParam String fileName) {
        Path path = Paths.get("src\\main\\resources\\history\\" + fileName + ".txt");
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        logger.info("Downloading file with the given filename {}",fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    public List<String> downloadListFromLocal() throws IOException {
        Path historyPath = Paths.get("src\\main\\resources\\history\\");

        List<String> fileList = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(historyPath)) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileList.add(path.getFileName()
                            .toString());
                }
            }
        }
        logger.info("Download list of history files");
        return fileList;
    }

    public void deleteFileFromLocal() throws IOException {
        Path historyPath = Paths.get("src\\main\\resources\\history\\");

        List<String> fileList = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(historyPath)) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    System.out.println("deleting file path = " + path);
                    logger.info("All history files deleted!");
                    Files.deleteIfExists(path);
                }
            }
        }
    }

}
