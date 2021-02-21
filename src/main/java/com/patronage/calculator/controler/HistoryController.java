package com.patronage.calculator.controler;

import com.patronage.calculator.service.HistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/history")
@Api("This segment let You do simple operation with the history of the previous calculation in the txt files")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping("/download/{fileName}")
    @ApiOperation("Downloading {fileName} file from local hard drive")
    public ResponseEntity downloadFileFromLocal(@PathVariable String fileName) throws MalformedURLException {
        return historyService.downloadFileFromLocal(fileName);
    }

    @GetMapping("/list")
    @ApiOperation("Downloading the list of history files")
    public List<String> downloadListFromLocal() throws IOException {
        return historyService.downloadListFromLocal();
    }

    @DeleteMapping("/delete/list")
    @ApiOperation("Clearing all history files")
    public void deleteFileFromLocal() throws IOException {
        historyService.deleteFileFromLocal();
    }

    @GetMapping("/read/current")
    @ApiOperation("Read current file")
    public List<String> readCurrentLog() throws IOException {
        return historyService.readCurrentLog();
    }

    @GetMapping("read/history")
    @ApiOperation("Read current history")
    public List<String> readHistory(@RequestParam String fromDate, @RequestParam(required = false) String toDate)
            throws IOException{
        return historyService.readHistory(fromDate, toDate);
    }
}
