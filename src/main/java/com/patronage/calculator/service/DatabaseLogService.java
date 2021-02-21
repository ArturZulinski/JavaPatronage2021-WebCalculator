package com.patronage.calculator.service;

import com.patronage.calculator.entity.History;
import com.patronage.calculator.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnProperty(name = "H2_HISTORY_ENABLE", havingValue = "true")
public class DatabaseLogService implements HistoryInterface{

    @Autowired
    public HistoryRepository historyRepository;

    @Override
    public void saveHistory(String message) {
        History history = new History();
        history.setMessageOfLog(message);
        history.setTimeOfLog(LocalDateTime.now().withNano(0));

        historyRepository.save(history);
    }

    @Override
    public List<String> readHistory(String fromDate, String toDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime begin = LocalDateTime.parse(fromDate,formatter);
        List<History> output;
        if(toDate!=null){
            LocalDateTime end = LocalDateTime.parse(toDate,formatter);
            output = historyRepository.findByTimeOfLogBetween(begin,end);
        }
        else{
            output = historyRepository.findByTimeOfLogAfter(begin);
        }

        List<String> endResult = new ArrayList<>();
        for(int i=0; i< output.size(); i++){
            History currentLine = output.get(i);
            endResult.add(currentLine.toString());
        }

        return endResult;
    }

    @Override
    public void clearHistory() {
        historyRepository.deleteAll();
    }
}
