import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import utilies.CsvReader;

public class Controller {
    private CsvReader csvReader;

    @FXML
    private TextField accuracyTextArea;

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
    }
}
