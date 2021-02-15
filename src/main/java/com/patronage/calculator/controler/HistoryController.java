package com.patronage.calculator.controler;

import com.patronage.calculator.service.HistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/history")
@Api("This segment let You do simple operation with the history of the previous calculation in the txt files")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping("/download/{filenane}")
    @ApiOperation("Downloading {filename} file from local hard drive")
    public ResponseEntity downloadFileFromLocal(@RequestParam String fileName) {
        return historyService.downloadFileFromLocal(fileName);
    }

    @GetMapping("/history/list")
    @ApiOperation("Downloading the list of history files")
    public List<String> downloadListFromLocal() throws IOException {
        return historyService.downloadListFromLocal();
    }

    @DeleteMapping("/history/delete/list")
    @ApiOperation("Clearing all history files")
    public void deleteFileFromLocal() throws IOException {
    }
}
