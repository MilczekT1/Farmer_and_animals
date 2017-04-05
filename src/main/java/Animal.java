import lombok.Data;
import java.io.*;

@Data
public class Animal implements Serializable {
    static final long serialVersionUID = -8776502104304959804L;
    
    private String species;
    private int ageInMonths;
    private String gender;
    private int weightInKG;
    private int barnID;
    private boolean isVaccinated;
    
    public Animal() {
        setSpecies(null);
        setAgeInMonths(-1);
        setGender(null);
        setWeightInKG(0);
        setBarnID(-1);
        setVaccinated(false);
    }
    public Animal(String species, int ageInMonths, String gender, int weightInKG, boolean vaccinated, int barnID) {
        setSpecies(species);
        setAgeInMonths(ageInMonths);
        setGender(gender);
        setWeightInKG(weightInKG);
        setBarnID(barnID);
        setVaccinated(vaccinated);
    }
    
    public String showAnimal(){
        String vaccine = this.isVaccinated() ? "szczepiony" : "nie szczepiony";
        return getSpecies() + " | " + getGender() + " | waga " + weightInKG + " kg | "+ getAgeInMonths()/12 + " lat " + getAgeInMonths()%12 + " miesiecy | w stodole o ID: " + getBarnID() + " | " + vaccine;
    }
}