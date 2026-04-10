package cr.ac.una.puebasgraficas.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextFormatter;

/**
 *
 * @author takka_sama
 */
public class DateController extends Controller implements Initializable {

    @FXML
    private MFXTextField txtDateX;
    @FXML
    private MFXButton btnTry;
 @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadFormatDateOnTxt();
    }  
      @Override
    public void initialize() {
    }
    
    @FXML
    private void onActionBtnTry(ActionEvent event) {
    }
    
    
    private void loadFormatDateOnTxt(){
        TextFormatter<String> formatteDate = new TextFormatter<>(change -> {
             String newTxt = change.getControlNewText();
             
            if(newTxt.matches("\\d*")) return null;
                 
            if(newTxt.length()> 8 ) return null;
      
            if (newTxt.length() == 2 || newTxt.length() == 4) 
                Platform.runLater(()-> {txtDateX.setText(newTxt + "-");});
      
            return change;
            
    });
        txtDateX.setTextFormatter(formatteDate);
    }
}