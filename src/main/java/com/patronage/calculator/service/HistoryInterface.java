package com.patronage.calculator.service;

import java.io.IOException;
import java.util.List;

public interface HistoryInterface {

    void saveHistory(String message);

    List<String> readHistory(String fromDate, String  toDate) throws IOException;

}
