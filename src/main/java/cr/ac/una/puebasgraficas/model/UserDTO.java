
package cr.ac.una.puebasgraficas.model;
    import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 *
 * @author takka_sama
 */
public class UserDTO {
    private StringProperty name;
    private StringProperty email;
    private ObjectProperty<LocalDate> dateBirth;

/**
 *
 * Property Gets And Sets
 * 
 */
    public UserDTO(String name, String email, LocalDate dateBirth){
        setName(name);
        setEmail(email);
        setDateBirth(dateBirth);
        
    }
    
    public UserDTO() {
        this.name = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.dateBirth = new SimpleObjectProperty<>(LocalDate.now());
    }


    public StringProperty getNameProperty() {
        return name;
    }

    public void setNameProperty(StringProperty name) {
        this.name = name;
    }

    public StringProperty getEmailProperty() {
        return email;
    }

    public void setEmailProperty(StringProperty email) {
        this.email = email;
    }

    public ObjectProperty<LocalDate> getDateBirthProperty() {
        return dateBirth;
    }

    public void setDateBirthProperty(ObjectProperty<LocalDate> dateBirth) {
        this.dateBirth = dateBirth;
    }
   
/**
 *
 * Normal Gets and Sets
 * 
 */
   
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

      public LocalDate getDateBirth(){
        return dateBirth.get();
    }
    
    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth.set(dateBirth);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String toString() {
        
        return getName() + " : " + getEmail() + "\t-\t" + getDateBirth().toString();
    
    }
    
    
   
}
