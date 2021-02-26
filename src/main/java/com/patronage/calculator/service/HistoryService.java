package com.patronage.calculator.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryService{


    private static final Logger logger = LogManager.getLogger(HistoryService.class);

    public ResponseEntity downloadFileFromLocal(String fileName) throws MalformedURLException {
        Path path = Paths.get("logs\\" + fileName + ".txt");
        Resource resource = new UrlResource(path.toUri());

        String message = String.format("Downloading file with the given filename %s",fileName);

        logger.info(message);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    public List<String> downloadListFromLocal() throws IOException {
        Path historyPath = Paths.get("logs\\");

        List<String> fileList = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(historyPath)) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileList.add(path.getFileName()
                            .toString());
                }
            }
        }
        String message = "Download list of history files";
        logger.info(message);
        return fileList;
    }

    public void deleteFileFromLocal() throws IOException {
        Path historyPath = Paths.get("logs\\");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(historyPath)) {
            for (Path path : stream) {
                if(!Files.isDirectory(path) && !("operation_history.txt".equals(path.getFileName().toString()))){
                    logger.info("The {} history file  deleted!",path);
                    Files.deleteIfExists(path);
                }
            }
        }
        logger.info("All files deleted");
    }


    public List<String> readCurrentLog() throws IOException {
        Path historyPath = Paths.get("logs\\operation_history.txt");
        List<String> strings = Files.readAllLines(historyPath);

        String message = "Reading file with current operations";
        logger.info(message);
        return strings;
    }
}
