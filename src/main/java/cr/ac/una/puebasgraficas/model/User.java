
package cr.ac.una.puebasgraficas.model;

import cr.ac.una.puebasgraficas.util.GsonUtil;
import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author takka_sama
 */
public class User {
    
    String name;
    String email;
    String dateBirth;

    public User(String name, String email, String dateBirth) {
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

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }
    
    public static boolean isValidDate(LocalDate dateToVerify){
        return Period.between(dateToVerify, LocalDate.now()).getYears() > 18;
   
    }

    @Override
    public String toString() {
        return getName() + " : " + getEmail() + "\t-\t" + getDateBirth();
    }
    
}
