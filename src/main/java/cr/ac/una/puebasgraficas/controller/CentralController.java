package cr.ac.una.puebasgraficas.controller;

import cr.ac.una.puebasgraficas.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author chela
 */
public class CentralController extends Controller implements Initializable {

    @FXML
    private MFXButton btnDrapAndDrop;
    @FXML
    private MFXButton btnTestDemoTowerDenfender;
    @FXML
    private MFXButton btnTestTTS;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @Override
    public void initialize() {
        setNombreVista("Central");
    }

    @FXML
    private void onActionBtnDrapAndDrop(ActionEvent event) {
    }

    @FXML
    private void onActionBtnTestDemoTowerDenfender(ActionEvent event) {
        FlowController.getInstance().goMain("TowerDefense");
    }

    @FXML
    private void onActionBtnTestTTS(ActionEvent event) {
       FlowController.getInstance().goMain("VoicesTest");
    }
    
}
