package com.pwbsoft.jserialmailer.service;

import com.pwbsoft.jserialmailer.data.Recipient;
import com.pwbsoft.jserialmailer.exception.ApplicationException;
import com.pwbsoft.jserialmailer.exception.Error;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CSVService {

    public List<Recipient> getRecipientsFromCSV(String pathToCSV) throws ApplicationException {
        try (var parser = readCSV(pathToCSV)) {
            var headerNames = parser.getHeaderNames();

            return parser.stream().map(row -> {
                var recipient = new Recipient();
                recipient.setEmail(row.get("email"));
                headerNames.forEach(header -> {
                    recipient.getData().put(header, row.get(header));
                });
                return recipient;
            }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private CSVParser readCSV(String path) throws ApplicationException {

        var file = new File(path);

        if (!file.exists()) {
            throw new ApplicationException(Error.FILE_NOT_FOUND, path);
        }

        try {
            return new CSVParser(new FileReader(file), CSVFormat.DEFAULT.builder().setHeader().build());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
