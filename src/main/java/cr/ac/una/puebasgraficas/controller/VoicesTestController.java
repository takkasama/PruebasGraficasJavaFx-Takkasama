package cr.ac.una.puebasgraficas.controller;

import cr.ac.una.puebasgraficas.model.ListUsers;
import cr.ac.una.puebasgraficas.model.Token;
import cr.ac.una.puebasgraficas.model.User;
import cr.ac.una.puebasgraficas.service.*;
import cr.ac.una.puebasgraficas.util.AppContext;
import cr.ac.una.puebasgraficas.util.AudioManager;
import cr.ac.una.puebasgraficas.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class VoicesTestController extends Controller implements Initializable {

    private ListUsers listUsersUI = new ListUsers();
    
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
    private Label lblTopInfo;
    @FXML
    private AnchorPane testPane;
    @FXML
    private MFXTextField txtNumber;
    @FXML
    private MFXTextField txtLetter;
    @FXML
    private MFXTextField txtStation;
    @FXML
    private MFXButton btnAddUser;
    @FXML
    private MFXButton btnReproduceManager;
    @FXML
    private MFXTextField txtDescriptionPiper;
    @FXML
    private MFXButton btnReproducePiper;
    @FXML
    private MFXTextField txtDescriptionFree;
    @FXML
    private MFXButton btnReproduceFree;
    @FXML
    private MFXButton btnExit;


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
    private void onActionBtnAddUser(ActionEvent event) {
                saveAndVerifyUser();
    }

    @FXML
    private void onActionBtnReproducePiper(ActionEvent event) {
        if(txtDescriptionPiper.getText().isBlank()) return;
        PiperTTSService.getInstance().speak(txtDescriptionPiper.getText());
        
    }
    
 @FXML
    private void onKeyPressedDescripcionPiper(KeyEvent event) {
        onActionBtnReproducePiper(null);
    }

    @FXML
    private void onActionBtnReproduceFree(ActionEvent event) {
        if(txtDescriptionFree.getText().isBlank()) return;
        FreeTTS.getInstance().speak(txtDescriptionFree.getText());
    }
    
    @FXML
    private void onKeyPressedDescripcionFree(KeyEvent event) {
         onActionBtnReproduceFree(null);
    }
    
    @FXML
    private void onActionBtnReproduceManager(ActionEvent event) {
         executeCalled();
    }    
    
    @FXML
    private void onActionBtnExit(ActionEvent event) {
        FlowController.getInstance().goMain();
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
        else
            try {
                PiperTTSService.getInstance().speak(user.mensajeDeConfirmacion());
        } catch (Exception ex) {
            System.getLogger(VoicesTestController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
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

    private void executeCalled() {

        String currentNum = txtNumber.getText(); // ✅ corregido
        String currentLetter = txtLetter.getText();
        String currentStation = txtStation.getText();

        // Validar vacíos
        if (currentNum == null || currentNum.trim().isEmpty() ||
            currentLetter == null || currentLetter.trim().isEmpty() ||
            currentStation == null || currentStation.trim().isEmpty()) {

            System.out.println("Campos vacíos");
            return;
        }

        try {
            int numero = Integer.parseInt(currentNum);

            Token token = new Token(
                    numero,
                    currentLetter.toUpperCase(),
                    currentStation
            );

            AudioManager.getINSTANCIA().callToken(token);

        } catch (NumberFormatException e) {
            System.out.println("Número inválido");
        }
    }

    
}

    


