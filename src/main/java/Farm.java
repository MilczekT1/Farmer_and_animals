import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

public final class Farm implements Serializable {
    private static final long serialVersionUID = 6071520850077189150L;
    
    public String farmID;
    private int barnsAmount;
    private ArrayList<Barn> barns;
    
    public Farm(String farmID) {
        this.barns = new ArrayList<>();
        this.barnsAmount = 0;
        this.farmID = farmID;
    }
    public Farm(Farm f){
        this.barns = f.getBarns();
        this.barnsAmount = f.getBarnsAmount();
        this.farmID = f.getFarmID();
    }
    
    public int getFarmAnimalsAmount(){
        int amount = 0;
        for (Barn barn : barns){
            amount += barn.getAnimalsAmount();
        }
        return amount;
    }
    public Map<String, Integer> getAllAnimals(){
        LinkedList<Map<String,Integer>> maplist = new LinkedList<>();
        
        for (Barn barn : barns){
            if (barn.getAnimalsAmount() != 0){
                maplist.add(barn.getSpeciesAmounts());
            }
        }
        
        Map<String,Integer> animals = new HashMap<>();
        
        Integer value;
        for (Map<String,Integer> barnAnimals : maplist){
            if (barnAnimals.equals(animals)){
                continue;
            }
            else{
                for (String key : barnAnimals.keySet()){
                    value = barnAnimals.get(key);
                    if (!animals.containsKey(key)){
                        animals.put(key,value);
                    }
                    else{
                        animals.put(key,++value);
                    }
                }
            }
        }
        return animals;
    }
    
    public void removeBarn(Barn barn){
        if (barns.contains(barn)){
            barns.remove(barn);
            barnsAmount--;
        }
        else{
            System.out.println("Nie ma takiej stodoly.");
        }
    }
    public void removeBarn(String name, int capacity){
        Barn barn= new Barn(name,capacity);
        if (barns.contains(barn)){
            barns.remove(barn);
            barnsAmount--;
        }
        else{
            System.out.println("Nie ma takiej stodoly.");
        }
    }
    public void addBarn(Barn barn){
        if (!barns.contains(barn)) {
            barns.add(barn);
            barnsAmount++;
        }
        else{
            System.out.println("Nie podano unikalnej nazwy stodoly!");
        }
    }
    
    
    public String getFarmID() {
        return farmID;
    }
    public int getBarnsAmount() {
        return barnsAmount;
    }
    public ArrayList<Barn> getBarns() {
        return barns;
    }
    
    public void saveFarm(){
        writeObject();
        
        String pathToFileWithFarmName = FarmSimulator.workDir.toString() + "/" + "Farm_Name.txt";
        File myFile = new File(pathToFileWithFarmName);
        if(!myFile.exists()) {
            try {
                myFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Files.write(myFile.toPath(), (this.getFarmID() + "\n").getBytes(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeObject(){
        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(new FileOutputStream((FarmSimulator.workDir.toString() + "/" + this.getFarmID() + ".txt")));
            out.writeObject(this);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFarmIfExists(Farm farm){
    
        String pathToFileWithFarmName = FarmSimulator.workDir.toString() + "/" + "Farm_Name.txt";
        File myFile = new File(pathToFileWithFarmName);
        
        String farmID="error";
        boolean ok = false;
        try {
            List<String> rows = Files.readAllLines(myFile.toPath());
            farmID = rows.get(0);
            ok = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (ok) {
            Farm f1;
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FarmSimulator.workDir.toString() + "/" + farmID + ".txt"))) {
                f1 = (Farm) in.readObject();
    
                this.barns = f1.getBarns();
                this.barnsAmount = f1.getBarnsAmount();
                this.farmID = (String) f1.getFarmID();
                
            } catch (FileNotFoundException e) {
                //TODO: log it
            } catch (IOException e) {
                //TODO: log it
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public String toString() {
        return "Farma { " + "ID=" + farmID + ", Liczba_Stodól= " + barnsAmount + ", barns=" + barns + '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        
        Farm farm = (Farm) o;
    
        return getFarmID().equals(farm.getFarmID());
    }
    
    @Override
    public int hashCode() {
        return getFarmID().hashCode();
    }
}