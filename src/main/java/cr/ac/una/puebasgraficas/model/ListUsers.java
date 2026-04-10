
package cr.ac.una.puebasgraficas.model;

import cr.ac.una.puebasgraficas.util.GsonUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 *
 * @author takka_sama
 */
public class ListUsers {
    
    private ObservableList<User> listUsersUI;
    
    public ListUsers(){
        this.listUsersUI = FXCollections.observableArrayList();
    }

    public ObservableList<User> getListUsersUI() {
        return listUsersUI;
    }
    
    public boolean add(User newUser){
        
       if (newUser == null || exist(newUser)) return false;
       
       listUsersUI.add(newUser);
       
       GsonUtil.guardar(listUsersUI, "Users.json");
              
       return true;
    }
    
    public boolean remove(User userToRemove){
        
        if(userToRemove == null || !exist(userToRemove)) return false;
        
        listUsersUI.removeIf(u -> u.getEmail().equals(userToRemove.getEmail()));
        
        
        return true;

    }
    
    public boolean exist(User user){
        
        if (user == null || listUsersUI.isEmpty()) return false;
        
        
        for (User userToFind : listUsersUI){
            
            if ( user.getEmail().equals(userToFind.getEmail()) )return true;
            
        }
        
        return false;
        
    }
    
    public void loadData(){
        
        if(!GsonUtil.existe("Users.json")) return; 
        
        List<User> users = GsonUtil.leerLista("Users.json", User.class);
        
        listUsersUI = FXCollections.observableList(users);  
        
    }
     

 
    
}
