import lombok.Data;
import java.io.*;

@Data
public class Animal implements Serializable {
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
        setWeightInKG(0);
        setBarnID(-1);
        setBarnName("youngWildAndFree");
        setVaccinated(false);
    }
    public Animal(String species, int ageInMonths, String gender, int weightInKG, boolean vaccinated, int barnID, String barnName) {
        setSpecies(species);
        setAgeInMonths(ageInMonths);
        setGender(gender);
        setWeightInKG(weightInKG);
        setBarnID(barnID);
        setBarnName(barnName);
        setVaccinated(vaccinated);
    }
    
    public String showAnimal(){
        String vaccine = this.isVaccinated() ? "vaccinated" : "not vaccinated";
        return getSpecies() + " | " + getGender() + " | weight " + weightInKG + " kg | "+ getAgeInMonths()/12 + " years " + getAgeInMonths()%12 + " months | in barn with name: " + getBarnName() + " | " + vaccine;
    }
}