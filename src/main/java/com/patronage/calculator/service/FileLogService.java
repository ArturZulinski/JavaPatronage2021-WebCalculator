package com.patronage.calculator.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ConditionalOnProperty(name = "H2_HISTORY_ENABLE", value = "false", matchIfMissing = true)
@Component
public class FileLogService implements HistoryInterface{

    private static final Logger logger = LogManager.getLogger("toFile");

    @Override
    public void saveHistory(String message) {
        logger.info(message);
    }

    @Override
    public List<String> readHistory(LocalDateTime fromDate, LocalDateTime toDate) throws IOException {
        Path path = Paths.get("logs\\operation_history.txt");
        List<String> strings = Files.readAllLines(path);
        List<String> output = new ArrayList<>();

        for(int i=0 ; i < strings.size() ; i++){
            String currentLine = strings.get(i);
            String actualDate = currentLine.substring(0,19);
            LocalDateTime actualDateTime = LocalDateTime.parse(actualDate);
            if((actualDateTime.isAfter(fromDate)) && ((toDate == null) || (actualDateTime.isBefore(toDate)))){
                output.add(currentLine);
            }
        }

        return output;
    }
}
