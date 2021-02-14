package com.patronage.calculator.controler;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HistoryControler {

    @GetMapping("/download/{filenane}")
    public ResponseEntity downloadFileFromLocal(@RequestParam String fileName) {
        Path path = Paths.get("src\\main\\resources\\history\\" + fileName + ".txt");
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/history/list")
    public List<String> downloadFileFromLocal() throws IOException {
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
        return fileList;
    }

    @DeleteMapping("/history/delete/list")
    public void dleteFileFromLocal() throws IOException {
        Path historyPath = Paths.get("src\\main\\resources\\history\\");

        List<String> fileList = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(historyPath)) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    System.out.println("deleting file path = " + path);
                    Files.deleteIfExists(path);
                }
            }
        }
    }
}
