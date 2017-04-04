import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Barn implements Serializable {
    private static final long serialVersionUID = 9081580776763231710L;
    
    private static int barnCounter = 0;
    private String name;
    private int barnID;
    private int capacity;
    private int animalsAmount;
    private ArrayList<Animal> animalsInBarn;
    
    public Barn(String name) {
        setName(name);
        setAnimalsAmount(0);
        this.animalsInBarn = new ArrayList<>();
    }
    public Barn(String name, int capacity) {
        setName(name);
        setBarnID(++barnCounter);
        setCapacity(capacity);
        setAnimalsAmount(0);
        this.animalsInBarn = new ArrayList<>();
    }
    
    public Animal getTheOldest(){
        int maxAge=Integer.MIN_VALUE;
        Animal theOldest = new Animal();
    
        if (animalsAmount == 0) {
            System.out.println("Blad, stodola jest pusta!");
        }
        else {
            for (Animal animal : animalsInBarn) {
                if (animal.getAgeInMonths() > maxAge) {
                    maxAge = animal.getAgeInMonths();
                    theOldest = animal;
                }
            }
        }
        return theOldest;
    }
    public Animal getTheYoungest(){
        int minAge=Integer.MAX_VALUE;
        Animal theYoungest = new Animal();
        if (animalsAmount == 0) {
            System.out.println("Blad, stodola jest pusta!");
        }
        else {
            for (Animal animal : animalsInBarn) {
                if (animal.getAgeInMonths() < minAge) {
                    minAge = animal.getAgeInMonths();
                    theYoungest = animal;
                }
            }
        }
        return theYoungest;
    }
    
    public void addAnimal(Animal animal){
        if (isFull()){
            System.out.println("Stodola jest pelna. Dodaj zwierzę do innej stodoly");
        }
        else{
            animalsInBarn.add(animal);
            animalsAmount++;
        }
    }
    public void addAnimals(List<Animal> listOfAnimals){
        animalsInBarn.addAll(listOfAnimals);
        animalsAmount += listOfAnimals.size();
    }
    public void removeAnimal(String species,String gender, int ageInMonths, int weightInKG){
        Animal animal = new Animal (species, ageInMonths,gender,weightInKG,false, barnID);
        if (animalsInBarn.contains(animal)){
            animalsInBarn.remove(animal);
            animalsAmount--;
        }
        else{
            System.out.println("Brak takiego zwierzęcia w stodole.");
        }
    }
    public void removeAnimal(Animal animal){
        if (animalsInBarn.contains(animal)){
            animalsInBarn.remove(animal);
            animalsAmount--;
        }
        else{
            System.out.println("Brak takiego zwierzęcia w stodole.");
        }
    }
    
    public Map<String,Integer> getSpeciesAmounts(){
        Map<String,Integer> animals = new HashMap<>();
        String key;
        Integer value;
        for(Animal animal : animalsInBarn){
            key = animal.getSpecies();
            if (!animals.containsKey(key)){
                animals.put(key,1);
            }
            else{
                value = animals.get(key);
                animals.put(key,++value);
            }
        }
        return animals;
    }
    
    public boolean isFull(){
        return animalsInBarn.size() == capacity ? true : false ;
    }
    
    public String getName() {
        return name;
    }
    private void setName(String name) {
        this.name = name;
    }
    
    public int getBarnID() {
        return barnID;
    }
    public void setBarnID(int barnID) {
        this.barnID = barnID;
    }
    
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public int getAnimalsAmount() {
        return animalsAmount;
    }
    public void setAnimalsAmount(int animalsAmount) {
        this.animalsAmount = animalsAmount;
    }
    
    public ArrayList<Animal> getAllVaccined(){
        ArrayList<Animal> vaccined = new ArrayList<>();
        for (Animal animal : animalsInBarn){
            if (animal.isVaccinated()){
                vaccined.add(animal);
            }
        }
        return vaccined;
    }
    public ArrayList<Animal> getAnimalsInBarn() {
        return animalsInBarn;
    }
    
    public void setAnimalsInBarn(ArrayList<Animal> animalsInBarn) {
        this.animalsInBarn = animalsInBarn;
    }
    
    @Override
    public String toString() {
        return "Barn{" + "Nazwa=" + getName() + ", ID=" + barnID + ", Pojemnosc=" + capacity + ", LiczbaZwierzat=" + animalsAmount + ", ZwierzetaWStodole=" + animalsInBarn + '}';
    }
    
    @Override
    
    public boolean equals(Object o) {
        //every barn is unique at it's farm, identified by it's name.
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        
        Barn barn = (Barn) o;
    
        return getName().equals(barn.getName());
    }
    
    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}