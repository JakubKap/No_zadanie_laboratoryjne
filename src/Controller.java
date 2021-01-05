import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.JelinskiMoranda;
import models.SchickWolverton;
import utilies.CsvReader;

public class Controller {
    private CsvReader csvReader;

    @FXML
    private TextField accuracyTextField;

    @FXML
    private Button fileButton;

    @FXML
    private Label selectedFileLabel;

    @FXML
    private Button acceptParametersButton;

    @FXML
    private TextArea calcResultTextArea;

    @FXML
    private void fileButtonOnMouseClicked(){
        csvReader = new CsvReader();
        csvReader.readCsvFile();

        selectedFileLabel.setText(csvReader.getFilePath());
        selectedFileLabel.setVisible(true);
    }

    @FXML
    private void acceptParametersButtonOnMouseClicked(){
        csvReader.readDataToArray();
        JelinskiMoranda jelinskiMoranda = new JelinskiMoranda(Double.parseDouble(accuracyTextField.getText()),
                csvReader.getFaultTimesArray());
        jelinskiMoranda.estimateBigNAndFi();

        calcResultTextArea.appendText("Metoda Jelińskiego-Morandy:");
        calcResultTextArea.appendText("\n\nWybrana dokładność: " + accuracyTextField.getText());
        calcResultTextArea.appendText("\nLiczba dotychczas wykrytych błędów: " + jelinskiMoranda.getSmallN());
        calcResultTextArea.appendText("\nWyznaczony parametr N: " + jelinskiMoranda.getBigN());
        calcResultTextArea.appendText("\nWyznaczony parametr fi: " + jelinskiMoranda.getFi());
        calcResultTextArea.appendText("\nWyznaczona wartość oczekiwana czasu, ");
        calcResultTextArea.appendText("\njaki upłynie do momentu wykrycia " +
                jelinskiMoranda.getNEXT_FAULT_TIME() + " błędu: " + jelinskiMoranda.getEt());

        SchickWolverton schickWolverton = new SchickWolverton(Double.parseDouble(accuracyTextField.getText()),
                csvReader.getFaultTimesArray());
        schickWolverton.estimateBigNAndFi();

        calcResultTextArea.appendText("\n\nMetoda Schicka-Wolvertona:");
        calcResultTextArea.appendText("\n\nWybrana dokładność: " + accuracyTextField.getText());
        calcResultTextArea.appendText("\nLiczba dotychczas wykrytych błędów: " + schickWolverton.getSmallN());
        calcResultTextArea.appendText("\nWyznaczony parametr N: " + schickWolverton.getBigN());
        calcResultTextArea.appendText("\nWyznaczony parametr fi: " + schickWolverton.getFi());
        calcResultTextArea.appendText("\nWyznaczona wartość oczekiwana czasu, ");
        calcResultTextArea.appendText("\njaki upłynie do momentu wykrycia " +
                schickWolverton.getNEXT_FAULT_TIME() + " błędu: " + schickWolverton.getEt());

    }
}
