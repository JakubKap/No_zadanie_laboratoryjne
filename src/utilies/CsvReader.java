package utilies;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    private String filePath;
    private List<Integer> csvNumbers;
    private Integer[] faultTimesArray;

    public CsvReader() {
        this.csvNumbers = new ArrayList<>();
    }

    public void readCsvFile(){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Plik .csv", "csv");
        fileChooser.setFileFilter(filter);
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);

        int returnVal = fileChooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            filePath = fileChooser.getSelectedFile().getPath();
        }
    }

    public void readDataToArray(){
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String SEMICOLON_DELIMITER = ";";
                String[] values = line.split(SEMICOLON_DELIMITER);
                for(String value : values)
                    csvNumbers.add(new Integer(value));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        faultTimesArray = csvNumbers.toArray(new Integer[0]);
    }

    public String getFilePath() {
        return filePath;
    }

    public Integer[] getFaultTimesArray() {
        return faultTimesArray;
    }
}
