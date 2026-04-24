
package cr.ac.una.puebasgraficas.model;

/**
 *
 * @author chela
 */
public class Token {
    
    int number;
    String letter;
    String station;

    public Token(){}
    
    public Token(int number, String letter, String station) {
        this.number = number;
        this.letter = letter;
        this.station = station;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getCode(){
        return letter + number;
    }
    @Override
    public String toString() {
        return "Token{" + "number=" + number + ", letter=" + letter + ", station=" + station + '}';
    }
    
}
