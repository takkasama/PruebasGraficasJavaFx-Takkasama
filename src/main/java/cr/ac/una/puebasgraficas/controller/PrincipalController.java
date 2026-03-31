package cr.ac.una.puebasgraficas.controller;

import cr.ac.una.puebasgraficas.model.ListUsers;
import cr.ac.una.puebasgraficas.model.UserDTO;
import cr.ac.una.puebasgraficas.model.User;
import cr.ac.una.puebasgraficas.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PrincipalController extends Controller implements Initializable {

    private ListUsers listUsersUI = new ListUsers();
    
    @FXML
    private MFXButton btnAgregar;
    @FXML
    private MFXTextField txtName;
    @FXML
    private MFXTextField txtEmail;
    @FXML
    private ListView<UserDTO> ListUserContainer;
    @FXML
    private AnchorPane pContainer;
    @FXML
    private MFXDatePicker dpkDateBirth;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ListUserContainer.setItems(listUsersUI.getListUsersUI());
    }    

 @Override
    public void initialize() {
    }
    
    
    @FXML
    private void onActionBtnAgregar(ActionEvent event) {
      
        boolean isEmptyTxtFieldEmpty = txtEmail.getText().isEmpty() || 
                txtName.getText().isEmpty();          
        
        Stage paretStage = (Stage) pContainer.getScene().getWindow();

        if(isEmptyTxtFieldEmpty){
            openAdvertisementWindow(paretStage, "Txt Empty");
            return;
        }
        if(!isValidNameTextField(txtName.getText())){
            openAdvertisementWindow(paretStage, "Invalid Name");
            return;
        }
         if(!isValidEmailTextField(txtEmail.getText())){
            openAdvertisementWindow(paretStage, "Invalid Email");
            return;
        }
         if(!isValidDateBirthDatePicker(dpkDateBirth.getValue())){
             openAdvertisementWindow(paretStage, "Invalid Date Birth");
         }
        else 
            saveUserAndLoadUser(txtName.getText(), txtEmail.getText(), dpkDateBirth.getValue());
        
                    

    }
   
    public void saveUserAndLoadUser(String name, String email, LocalDate dateBirth){
        
        UserDTO user = new UserDTO();
        user.setName(name);
        user.setEmail(email);
        user.setDateBirth(dateBirth);
        
        if(!listUsersUI.add(user)){
            
            Stage parentStage = (Stage) pContainer.getScene().getWindow();
            //Obtenemos el estage de la vista en la cual se trababaja donde se puede entender de forma -> del contedor principal se obtendra el stage 
            openAdvertisementWindow( parentStage ,"The User Exist");
        }
        
    }
    
    public void openAdvertisementWindow(Stage stage, String msgToShow){
        
        //Stage -> se recibe directamente debido a que si se obtien de la fomar FlowController.getIntance().getController(viewName).getStage 
        //Puede ser inestable.
        // Pendiente : Cambio de Mensaje de notificacion del anuncio a hacer
        
        AdvertisementController advertisementController = 
                (AdvertisementController) FlowController.getInstance().getController("AdvertisementView");
        
        advertisementController.setTextAdvertisement(msgToShow);
        FlowController.getInstance().goViewInWindowModal("AdvertisementView", stage, true);   
        
    }
 
    boolean isValidNameTextField(String textTovalidate){
        return textTovalidate.matches("[A-Za-z\\s]{4,30}");
    }
    
    boolean isValidEmailTextField(String textToValidate){
        return textToValidate.matches("^[a-zA-Z0-9._^\\-]{4,50}@[a-zA-Z.]{4,30}\\.[a-z]{2,3}$");
    }
    boolean isValidDateBirthDatePicker(LocalDate dateToValidate){     
        return !(dateToValidate == null || !(User.isValidDate(dateToValidate)));
    }
   
    private void limitDateToBirthDate(){
        
        //en desarrollo
        
    }
}

    


