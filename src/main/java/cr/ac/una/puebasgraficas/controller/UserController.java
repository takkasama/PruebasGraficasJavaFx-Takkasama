package cr.ac.una.puebasgraficas.controller;

import cr.ac.una.puebasgraficas.model.User;
import cr.ac.una.puebasgraficas.util.AppContext;
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
    private Label lblRegisterDate;
    
    User currentUser;

    Controller parentContoller;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUser();
    }   
    @Override
    public void initialize(){
    
        
    }
    
    @Override
    public void setParent(Controller parent){
        this.parentContoller = parent;
    }
    
    private void setUser(){
        
        currentUser = (User) AppContext.getInstance().get("CurrentUser");
        
        if (currentUser != null)
            loadUser();
        
    }
    private void loadUser(){        
        lblName.setText(currentUser.getName());
        lblRegisterDate.setText(currentUser.getRegisterDate());
        lblEmail.setText(currentUser.getEmail());
    }
    
}
