package com.patronage.calculator.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnProperty(name = "H2_HISTORY_ENABLE", havingValue = "false", matchIfMissing = true)
public class FileLogService implements HistoryInterface{

    private static final Logger logger = LogManager.getLogger("toFile");

    @Override
    public void saveHistory(String message) {
        logger.info(message);
    }

    @Override
    public List<String> readHistory(String fromDate, String toDate) throws IOException {
        Path path = Paths.get("logs\\operation_history.txt");
        List<String> strings = Files.readAllLines(path);
        List<String> output = new ArrayList<>();

        for(int i=0 ; i < strings.size() ; i++){
            String currentLine = strings.get(i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String actualDate = currentLine.substring(0,19);
            LocalDateTime actualDateTime = LocalDateTime.parse(actualDate,formatter);
            LocalDateTime begin = LocalDateTime.parse(fromDate,formatter);
            LocalDateTime end;
            if(toDate!=null){
                end = LocalDateTime.parse(toDate,formatter);
            }
            else {
                end = LocalDateTime.now();
            }

            if((actualDateTime.isAfter(begin)) && (actualDateTime.isBefore(end))){
                output.add(currentLine);
            }
        }
        logger.info("Reading history from {} to {}", fromDate, toDate);
        return output;
    }

    @Override
    public void clearHistory() throws IOException {
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
}
