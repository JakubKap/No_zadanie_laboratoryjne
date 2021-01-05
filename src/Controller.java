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
    private double accuracy;
    private Integer[] faultTimesArray;

    @FXML
    private TextField accuracyTextField;

    @FXML
    private Button fileButton;

    @FXML
    private Label selectedFileLabel;

    @FXML
    private Button acceptParametersButton;

    @FXML
    private Label errorLabel;

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
        errorLabel.setVisible(false);

        if(!areInputsValid()){
            errorLabel.setVisible(true);
            return;
        }

        if(calcResultTextArea.getText() != null)
            calcResultTextArea.clear();

        JelinskiMoranda jelinskiMoranda = new JelinskiMoranda(accuracy,faultTimesArray);
        jelinskiMoranda.estimateBigNAndFi();

        calcResultTextArea.appendText("Metoda Jelińskiego-Morandy:");
        calcResultTextArea.appendText("\n\nWybrana dokładność: " + accuracy);
        calcResultTextArea.appendText("\nLiczba dotychczas wykrytych błędów: " + jelinskiMoranda.getSmallN());
        calcResultTextArea.appendText("\nWyznaczony parametr N: " + jelinskiMoranda.getBigN());
        calcResultTextArea.appendText("\nWyznaczony parametr fi: " + jelinskiMoranda.getFi());
        calcResultTextArea.appendText("\nWyznaczona wartość oczekiwana czasu, ");
        calcResultTextArea.appendText("\njaki upłynie do momentu wykrycia " +
                jelinskiMoranda.getNEXT_FAULT_TIME() + " błędu: " + jelinskiMoranda.getEt());

        SchickWolverton schickWolverton = new SchickWolverton(accuracy, faultTimesArray);
        schickWolverton.estimateBigNAndFi();

        calcResultTextArea.appendText("\n\nMetoda Schicka-Wolvertona:");
        calcResultTextArea.appendText("\n\nWybrana dokładność: " + accuracy);
        calcResultTextArea.appendText("\nLiczba dotychczas wykrytych błędów: " + schickWolverton.getSmallN());
        calcResultTextArea.appendText("\nWyznaczony parametr N: " + schickWolverton.getBigN());
        calcResultTextArea.appendText("\nWyznaczony parametr fi: " + schickWolverton.getFi());
        calcResultTextArea.appendText("\nWyznaczona wartość oczekiwana czasu, ");
        calcResultTextArea.appendText("\njaki upłynie do momentu wykrycia " +
                schickWolverton.getNEXT_FAULT_TIME() + " błędu: " + schickWolverton.getEt());

    }

    private boolean areInputsValid(){
        try {
                accuracy = accuracyTextField.getText().isEmpty()
                        ? Double.parseDouble(accuracyTextField.getPromptText())
                        : Double.parseDouble(accuracyTextField.getText());
        } catch(NumberFormatException e){
            errorLabel.setText("Została wprowadzona zła wartość dokładności obliczeń -" +
                    " powinien być to ułamek w postaci dziesiętnej.");
            return false;
        }

        if (accuracy <= 0){
            errorLabel.setText("Dokładność obliczeń powinna być liczbą dodatnią.");
            return false;
        }
        else if(csvReader == null || csvReader.getFilePath() == null){
            errorLabel.setText("Nie został wybrany plik .csv.");
            return false;
        }

        if (csvReader.getFaultTimesArray() == null)
            csvReader.readDataToArray();
        faultTimesArray = csvReader.getFaultTimesArray();

        if(faultTimesArray.length == 0) {
            errorLabel.setText("Został wybrany pusty plik .csv.");
            return false;
        }

        return true;
    }
}
