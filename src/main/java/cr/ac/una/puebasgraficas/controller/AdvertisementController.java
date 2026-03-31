
package cr.ac.una.puebasgraficas.controller;

import cr.ac.una.puebasgraficas.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author takka_sama
 */
public class AdvertisementController extends Controller implements Initializable {

    private String textAdvertisement;
    @FXML
    private Label lblAdvertisement;
    @FXML
    private MFXButton btnAceptar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
     @Override
    public void initialize() {
    }

    @FXML
    private void onActionBtnAceptar(ActionEvent event) {
        FlowController.getInstance().getController("AdvertisementView").getStage().close();
    }   

    public void setTextAdvertisement(String textAdvertisement) {
       this.textAdvertisement = textAdvertisement;
       if(textAdvertisement != null)
           lblAdvertisement.setText(textAdvertisement);
    }
    
}
