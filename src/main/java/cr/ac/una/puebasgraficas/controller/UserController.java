package cr.ac.una.puebasgraficas.controller;

import cr.ac.una.puebasgraficas.model.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class UserController extends Controller implements Initializable {

    @FXML
    private Label lblName;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblDateBirth;


    @Override
    public void initialize(URL url, ResourceBundle rb) {}   
    @Override
    public void initialize(){}

    public void setTextNameUser(String nameUser){
        lblName.setText(nameUser);
    }
    
    public void setTextEmailUser(String emailUser){
        lblEmail.setText(emailUser);
    }
    
    public void setTextDateBirthUser(String dateBirth){
        lblDateBirth.setText(dateBirth);
    }
    
    public void setUserToShow(User user){
        
        setTextNameUser(user.getName());
        setTextEmailUser(user.getEmail());
        setTextDateBirthUser(user.getDateBirth());
        
    }
    
}
