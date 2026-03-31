
package cr.ac.una.puebasgraficas.model;

import cr.ac.una.puebasgraficas.util.GsonUtil;
import java.time.LocalDate;

/**
 *
 * @author takka_sama
 */
public class User {
    
    
    String name;
    String email;
    LocalDate dateBirth;

    public User(String name, String email, LocalDate dateBirth) {
        this.name = name;
        this.email = email;
        this.dateBirth = dateBirth;
    }
    
    public User(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }
    
    public static boolean isValidDate(LocalDate dateToVerify){
        
        boolean validYear = (LocalDate.now().getYear() - dateToVerify.getYear()) >= 18;
        boolean validMonth = LocalDate.now().getMonthValue() >= dateToVerify.getMonthValue();
        boolean validDay = LocalDate.now().getDayOfMonth() >= dateToVerify.getDayOfMonth();
  
        return validYear && validMonth;
    }
    
    public static void saveData(UserDTO userToSave){
        User newUser = new User(userToSave.getName(), userToSave.getEmail(), userToSave.getDateBirth());
        GsonUtil.guardar(newUser, "Users.json");
    }
    
}
