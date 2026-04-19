package cr.ac.una.puebasgraficas.controller;

import cr.ac.una.puebasgraficas.model.ListUsers;
import cr.ac.una.puebasgraficas.model.UserDTO;
import cr.ac.una.puebasgraficas.model.User;
import cr.ac.una.puebasgraficas.service.PiperTTSService;
import cr.ac.una.puebasgraficas.util.AppContext;
import cr.ac.una.puebasgraficas.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PrincipalController extends Controller implements Initializable {

    private ListUsers listUsersUI = new ListUsers();
    
    @FXML
    private MFXButton btnAgregar;
    @FXML
    private MFXTextField txtName;
    @FXML
    private MFXTextField txtEmail;
    @FXML
    private MFXLegacyTableView<User> tableUsers;
    @FXML
    private TableColumn<User, String> colName;
    @FXML
    private TableColumn<User, String> colEmail;
    @FXML
    private TableColumn<User, String> colDateBirth;
    @FXML
    private MFXTextField txtDescripcion;
    @FXML
    private MFXButton btnReproduceAudio;
    @FXML
    private MFXButton btnAbrir;
    @FXML
    private Label lblTopInfo;
    @FXML
    private AnchorPane testPane;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        colDateBirth.setCellValueFactory(new PropertyValueFactory<>("registerDate"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        tableUsers.setItems(listUsersUI.getListUsersUI());
    }    

    @Override
    public void initialize() {
        setNombreVista(" Pruebas Graficas ");
        loadTime();
    }
    
    
    @FXML
    private void onActionBtnAgregar(ActionEvent event) {
      
        saveAndVerifyUser();
                
    }
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *               
*                                                               |Guardado|                                                                  *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private void saveAndVerifyUser(){
        
        boolean isEmptyTxtFieldEmpty = txtEmail.getText().isEmpty() || 
                txtName.getText().isEmpty();          
        
        Stage paretStage = (Stage) testPane.getScene().getWindow();

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
        else 
            saveUserAndLoadUser(txtName.getText(), txtEmail.getText());
    }
    
    //Abre ventana emergente si no se cumple las condiciones de algo 
    public void openAdvertisementWindow(Stage stage, String msgToShow){
        
        //Stage -> se recibe directamente debido a que si se obtien de la fomar FlowController.getIntance().getController(viewName).getStage 
        //Puede ser inestable.
        // Pendiente : Cambio de Mensaje de notificacion del anuncio a hacer
        
        AdvertisementController advertisementController = 
                (AdvertisementController) FlowController.getInstance().getController("AdvertisementView");
        
        advertisementController.setTextAdvertisement(msgToShow);
        FlowController.getInstance().goViewInWindowModal("AdvertisementView", stage, true);   
        
    }
    
    //Agrega el usuario 
    public void saveUserAndLoadUser(String name, String email){
        
        User user = new User();
        
        user.setName(name);
        user.setEmail(email);
        user.setRegisterDate(LocalDate.now().toString());
        
        if(!listUsersUI.add(user)){
            Stage parentStage = (Stage) testPane.getScene().getWindow();
            openAdvertisementWindow(parentStage, " - THE USER EXIST - ");
        }
            
        
        
    }
    
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *               
*                                                               |Validaciones|                                                              *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

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

    @FXML
    private void onKeyPressedDescripcion(KeyEvent event) throws Exception {
        if(event.getCode() == KeyCode.ENTER) onActrionBtnReproduceAudio(null);
    }

    @FXML
    private void onActrionBtnReproduceAudio(ActionEvent event) throws Exception {
        PiperTTSService.getInstancia().sintetizar(txtDescripcion.getText());
    }


    @FXML
    private void onActionBtnAbrir(ActionEvent event) {
        
        
    }
    

    //  CARGAR INFORMACION EN EL TOP 
    
    
    private void loadTime(){
        
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
        
      Timeline time = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
          ZonedDateTime current = ZonedDateTime.now();
          lblTopInfo.setText(current.format(formatter));
        })
      );  
      
      time.setCycleCount(Timeline.INDEFINITE);
      time.play();
    } 

    @FXML
    private void onMouseClickedUser(MouseEvent event) {
      if(event.getClickCount() == 2){
            User current = tableUsers.getSelectionModel().getSelectedItem();
            if(current != null){
                AppContext.getInstance().set("CurrentUser", current);
                showAutoCloseUserView();
            }
        } 
    }
    
    private void showAutoCloseUserView(){
        
        FlowController.getInstance().goViewInWindowModal("UserView", getStage(), true);
        
        Timeline view = new Timeline(new KeyFrame(Duration.seconds(5), e ->{
                
                FlowController.getInstance().getController("UserView").getStage().close();
            })
        );
        
        view.setCycleCount(1);
        view.play();
    }

}

    


