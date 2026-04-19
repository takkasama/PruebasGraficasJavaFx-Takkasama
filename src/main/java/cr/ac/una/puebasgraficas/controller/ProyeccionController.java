package cr.ac.una.puebasgraficas.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;


/**
 * FXML Controller class
 *
 * @author chela
 */
public class ProyeccionController extends Controller implements Initializable {

    @FXML
    private VBox vbFichaPrincipal;
    @FXML
    private FlowPane flFichasSecundarias;
    @FXML
    private Label lblInfoEmpresa;
    @FXML
    private Label lblFecha;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @Override
    public void initialize() {
    }
    
    private void iniciarAutoRefescado(){
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> {refrescarVista();}));
        
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    
    private void refrescarVista(){
        
        
        
    }
    
}
