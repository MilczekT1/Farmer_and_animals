import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
final class Barn implements Serializable {
    private static final long serialVersionUID = 9081580776763231710L;
    
    private static int barnCounter = 0;
    private String name;
    private int barnID;
    private int capacity;
    private int animalsAmount;
    private ArrayList<Animal> animalsInBarn;
    
    public Barn(String name) throws Exception {
        if (name != null && name.trim() != "") {
            setName(name);
            setAnimalsAmount(-1);
            setCapacity(-1);
            setBarnID(-1);
            animalsInBarn = null;
        } else{
            throw new IllegalArgumentException("Invalid data in Barn constructor");
        }
    }
    public Barn(String name, int capacity) throws Exception {
        if (name != null & name.trim() != "" && capacity > 0) {
            setName(name);
            setBarnID(++barnCounter);
            setCapacity(capacity);
            setAnimalsAmount(0);
            this.animalsInBarn = new ArrayList<>();
        }
        else{
            throw new IllegalArgumentException("Invalid data in Barn constructor");
        }
    }
    
    public boolean isFull(){
        return animalsInBarn.size() == capacity ? true : false ;
    }
    
    public Animal getTheOldest(){
        int maxAge=Integer.MIN_VALUE;
        Animal theOldest = new Animal();
    
        if (animalsAmount == 0) {
            System.out.println("Mistake! Barn is empty!");
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
            System.out.println("Mistake! Barn is empty!");
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
            System.out.println("Mistake! Barn is empty!. Add animal to different barn");
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
    
    public void removeAnimal(Animal animal){
        if (animalsInBarn.contains(animal)){
            animalsInBarn.remove(animal);
            animalsAmount--;
        }
        else{
            System.out.println("There is no such animal in this barn.");
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
    public ArrayList<Animal> getAllVaccined(){
        ArrayList<Animal> vaccined = new ArrayList<>();
        for (Animal animal : animalsInBarn){
            if (animal.isVaccinated()){
                vaccined.add(animal);
            }
        }
        return vaccined;
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