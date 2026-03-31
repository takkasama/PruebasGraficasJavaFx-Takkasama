
package cr.ac.una.puebasgraficas.model;

import cr.ac.una.puebasgraficas.util.GsonUtil;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 *
 * @author takka_sama
 */
public class ListUsers {
    
    private ObservableList<UserDTO> listUsersUI;
    
    public ListUsers(){
        this.listUsersUI = FXCollections.observableArrayList();
    }

    public ObservableList<UserDTO> getListUsersUI() {
        return listUsersUI;
    }
    
    public boolean add(UserDTO newUser){
        
       if (newUser == null || exist(newUser)) return false;
       
       listUsersUI.add(newUser);
       
       User.saveData(newUser);
       
       return true;
    }
    
    public boolean remove(UserDTO userToRemove){
        
        if(userToRemove == null || !exist(userToRemove)) return false;
        
        listUsersUI.removeIf(u -> u.getEmail().equals(userToRemove.getEmail()));
        
        
        return true;

    }
    
    public boolean exist(UserDTO user){
        
        if (user == null || listUsersUI.isEmpty()) return false;
        
        
        for (UserDTO userToFind : listUsersUI){
            
            if ( user.getEmail().equals(userToFind.getEmail()) )return true;
            
        }
        
        return false;
        
    }
        

    public User convertUserDTOToUser(UserDTO userDTO){
       User user = new User();
       user.setDateBirth(userDTO.getDateBirth());
       user.setEmail(userDTO.getEmail());
       user.setName(userDTO.getName());
       
       return user;
   }
    
}
