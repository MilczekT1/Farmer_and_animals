import java.io.*;

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
    
    public String getSpecies() {
        return species;
    }
    private void setSpecies(String species) {
        this.species = species;
    }
    
    public int getAgeInMonths() {
        return ageInMonths;
    }
    private void setAgeInMonths(int ageInMonths) {
        this.ageInMonths = ageInMonths;
    }
    
    public String getGender() {
        return gender;
    }
    private void setGender(String gender) {
        this.gender = gender;
    }
    
    public int getWeightInKG() {
        return weightInKG;
    }
    public void setWeightInKG(int weightInKG) {
        this.weightInKG = weightInKG;
    }
    
    public int getBarnID() {
        return barnID;
    }
    public void setBarnID(int barnID) {
        this.barnID = barnID;
    }
    
    public boolean isVaccinated() {
        return isVaccinated;
    }
    public void setVaccinated(boolean vaccinated) {
        isVaccinated = vaccinated;
    }
    
    @Override
    public String toString() {
        return "Animal{" + "species='" + species + '\'' + ", ageInMonths=" + ageInMonths + ", gender='" + gender + '\'' + ", weightInKG=" + weightInKG + ", barnID=" + barnID + ", isVaccinated=" + isVaccinated + '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        
        Animal animal = (Animal) o;
        
        if (getAgeInMonths() != animal.getAgeInMonths())
            return false;
        if (weightInKG != animal.weightInKG)
            return false;
        if (getBarnID() != animal.getBarnID())
            return false;
        if (!getSpecies().equals(animal.getSpecies()))
            return false;
        return getGender().equals(animal.getGender());
    }
    
    @Override
    public int hashCode() {
        int result = getSpecies().hashCode();
        result = 31 * result + getAgeInMonths();
        result = 31 * result + getGender().hashCode();
        result = 31 * result + weightInKG;
        result = 31 * result + getBarnID();
        return result;
    }
}