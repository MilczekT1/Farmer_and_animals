import lombok.Data;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
final class Animal implements Serializable {
    static final long serialVersionUID = -8776502104304959804L;
    
    private String species;
    private String gender;
    private String barnName;
    private int ageInMonths;
    private int weightInKG;
    private int barnID;
    private boolean isVaccinated;
    
    public Animal() {
        setSpecies(null);
        setAgeInMonths(-1);
        setGender(null);
        setWeightInKG(-1);
        setBarnID(-1);
        setBarnName(null);
        setVaccinated(false);
    }
    public Animal(String species, int ageInMonths, String gender, int weightInKG, boolean vaccinated, int barnID, String barnName) throws Exception{
        if (species != null && gender != null && barnName != null &&
                    ageInMonths > 0 && weightInKG > 0 && barnID > 0 &&
                    Pattern.matches("male|female",gender) &&
                    barnName.trim() != "" && species.trim() != "") {
            
            setSpecies(species);
            setAgeInMonths(ageInMonths);
            setGender(gender);
            setWeightInKG(weightInKG);
            setBarnID(barnID);
            setBarnName(barnName);
            setVaccinated(vaccinated);
        } else {
            throw new IllegalArgumentException("Illegal arguments in constructor");
        }
    }
    
    public String showAnimal(){
        String vaccine = this.isVaccinated() ? "vaccinated" : "not vaccinated";
        return getSpecies() + " | " + getGender() + " | weight " + weightInKG + " kg | "+ getAgeInMonths()/12 + " years " + getAgeInMonths()%12 + " months | in barn with name: " + getBarnName() + " | " + vaccine;
    }
}