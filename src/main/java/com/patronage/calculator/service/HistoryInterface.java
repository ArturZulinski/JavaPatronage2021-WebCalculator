package com.patronage.calculator.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface HistoryInterface {

    void saveHistory(String message);

    List<String> readHistory(LocalDateTime fromDate, LocalDateTime toDate) throws IOException;

}
