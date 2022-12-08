package com.example.usercsvapp.helper;

import com.example.usercsvapp.model.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class HelperCSV {
    public static String TYPE = "text/csv";


    public boolean isCSVType(MultipartFile inputFile){
        log.info("### File content type: " + inputFile.getContentType());
        return inputFile.getContentType().equals(TYPE);
    }

    public List<UserDetails> transform(InputStream inputStream){
        try{
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreEmptyLines().withIgnoreHeaderCase().withTrim());

            List<UserDetails> usersList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord record: csvRecords){
                UserDetails user = new UserDetails(
                        record.get(0).toUpperCase(),
                        record.get(1).toUpperCase(),
                        record.get(3).toUpperCase(),
                        record.get(2).toUpperCase()
                );

                usersList.add(user);
            }
            return usersList;

        }catch (IOException e) {
            throw new RuntimeException("Unable to pars CSV file. Error message: " + e.getMessage());
        }
    }
}
