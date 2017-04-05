import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

//TODO: repair loaded data after new class version ( nulls - Serialization)

public final class FarmSimulator {
    
    private static boolean CONSOLE_MODE = true;
    public static Path workDir = Paths.get(System.getProperty("user.dir"),"/");
    private Farm farm;
    
    public static void main(String[] args) {
        FarmSimulator simulator = new FarmSimulator();
        
        if (CONSOLE_MODE){
            simulator.runInConsole(simulator);
        }
    }
    
    public FarmSimulator() {
        farm = new Farm("Farm 1");
    }
    
    private void runInConsole(FarmSimulator simulator){
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        farm.loadFarmIfExists(farm);
        
        simulator.printMenu();
        do {
            Integer choice = getIntegerWithRegex(in,"Choice: ","\\d*");
            
            switch (choice){
                case 0:     farm.saveFarm();
                            System.exit(0); break;
                
                case 1:   simulator.menu_addBarnToFarm(farm, in);                 break;
                case 2:   simulator.menu_removeBarnFromFarm(farm, in);            break;
                case 3:   simulator.menu_addAnimalToBarn(farm,in);                break;
                case 4:   simulator.menu_removeAnimalFromBarn(farm,in);           break;
                case 5:   simulator.menu_show_5_OldestAnimals(farm);              break;
                case 6:   simulator.menu_show_5_YoungestAnimals(farm);            break;
                case 7:   simulator.menu_showTheMostNumerousAnimal(farm);         break;
                case 8:   simulator.menu_showBarnWithMaxAnimals(farm);            break;
                case 9:   simulator.menu_showAllVaccinedAnimals(farm);            break;
                    
                default :   System.out.println("Choose again");               break;
            }
            
            printAnimalSummary(farm);
        }while(true);
    }
    
    private String getString(BufferedReader in){
        try {
            return in.readLine();
        }
        catch(IOException exc){
            System.out.println("Wrong data, try again");
            return null;
        }
    }
    private int getNumber(){
        Scanner in = new Scanner(System.in);
        try {
            int number = in.nextInt();
            return number;
        }
        catch(InputMismatchException exc){
            //TODO: log it
        }
        return -1;
    }
    
    private void printAllBarns(ArrayList<Barn> barns){
        System.out.println("---------");
        for (Barn barn: barns){
            System.out.print(barn.getName() + ": ");
            ArrayList<Animal> animalsFromBarn = barn.getAnimalsInBarn();
            if (animalsFromBarn.size() > 0) {
                System.out.println();
                for (Animal animal : animalsFromBarn) {
                    System.out.println(animal.showAnimal());
                }
            }
            else{
                System.out.println("empty");
            }
        }
        System.out.println("---------");
    }
    private void printMenu(){
        System.out.println("Menu");
        System.out.println("0 - Koniec");
        System.out.println("1 - Add barn");
        System.out.println("2 - Remove barn");
        System.out.println("3 - Add animal to barn");
        System.out.println("4 - Remove animal from barn");
        System.out.println("5 - Show 5 the oldest animals");
        System.out.println("6 - Show 5 the youngest animals");
        System.out.println("7 - Show the most numerous species");
        System.out.println("8 - Show barn with the most animals");
        System.out.println("9 - Show every vaccinated animals");
    }
    private void printAnimalSummary(Farm farm){
        Map<String, Integer> allAnimals = farm.getAllAnimals();
        if (allAnimals.size() > 0) {
            System.out.println("\n" + allAnimals);
        }
        else{
            System.out.println("Animals amount: 0");
        }
    }
    
    private String getStringWithRegex(BufferedReader inL, String message, String regex){
        String variable;
        do {
            System.out.print(message);
            variable = getString(inL);
        }while(!Pattern.matches(regex, variable));
        return variable;
    }
    private Integer getIntegerWithRegex(BufferedReader inL, String message, String regex){
        Integer variable;
        do {
            System.out.print(message);
            variable = getNumber();
        }while(!Pattern.matches(regex, variable.toString()));
        return variable;
    }
    
    private void menu_addBarnToFarm(Farm farm, BufferedReader inL){
        String barnName = getStringWithRegex(inL,"Barn name: ","\\D*\\d{0,4}");
        Integer capacity = getIntegerWithRegex(inL,"Barn capacity: ","\\d{1,}");
        farm.addBarn(new Barn(barnName, capacity));
    }
    private void menu_removeBarnFromFarm(Farm farm, BufferedReader inL){
        String barnName = getStringWithRegex(inL,"Barn name: ","\\D*\\d{0,4}");
        Integer capacity = getIntegerWithRegex(inL,"Barn capacity: ","\\d{1,}");
        farm.removeBarn(barnName, capacity);
    }
    
    private void menu_addAnimalToBarn(Farm farm, BufferedReader inL){
        if (farm.getBarnsAmount() > 0) {
            printAllBarns(farm.getBarns());
    
            String species = getStringWithRegex(inL,"Species: ","\\D*").toLowerCase();
            Integer ageInMonths = getIntegerWithRegex(inL,"Age in months: ","\\d{1,3}");
            Integer weight = getIntegerWithRegex(inL,"Weight (Kg): ","\\d{1,4}");
            String barnName = getStringWithRegex(inL,"Into Barn: ","\\D*\\d{0,4}");
    
            Integer genderINT = getIntegerWithRegex(inL,"Gender (0/1 female/male): ","[0-1]");
            String gender = (genderINT.equals(0)) ? "female" : "male";
            
            Integer vaccineINT = getIntegerWithRegex(inL,"Is vaccinated (1/0 true/false): ","[0-1]");
            boolean vaccined = (vaccineINT.equals(0)) ? false : true;

            ArrayList<Barn> barns = farm.getBarns();
            Barn tempBarn = new Barn(barnName);
            if (barns.contains(tempBarn)) {
                int index = barns.indexOf(tempBarn);
                int barnID = barns.get(index).getBarnID();
                barns.get(index).addAnimal(new Animal(species, ageInMonths, gender, weight, vaccined, barnID, barnName));
            }
            else{
                System.out.println("There is no such barn, can't put animal.");
            }
        }else{
            System.out.println("No barns, first add some barns!.");
        }
    }
    private void menu_removeAnimalFromBarn(Farm farm, BufferedReader inL){
        if (farm.getBarnsAmount() >0){
            printAllBarns(farm.getBarns());
    
            String species = getStringWithRegex(inL,"Species: ","\\D*").toLowerCase();
            Integer ageInMonths = getIntegerWithRegex(inL,"Age in months: ","\\d{1,3}");
            Integer weight = getIntegerWithRegex(inL,"Weight (Kg): ","\\d{1,4}");
            String barnName = getStringWithRegex(inL,"Into Barn: ","\\D*\\d{0,4}");
    
            Integer genderINT = getIntegerWithRegex(inL,"Gender (0/1 female/male): ","[0-1]");
            String gender = (genderINT.equals(0)) ? "female" : "male";
    
            Integer vaccineINT = getIntegerWithRegex(inL,"Is vaccinated (1/0 true/false): ","[0-1]");
            boolean vaccined = (vaccineINT.equals(0)) ? false : true;
            
            ArrayList<Barn> barns = farm.getBarns();
            Barn tempBarn = new Barn(barnName);
            if (barns.contains(tempBarn)) {
                int index = barns.indexOf(tempBarn);
                int barnID = barns.get(index).getBarnID();
                barns.get(index).removeAnimal(new Animal(species, ageInMonths, gender, weight, vaccined, barnID, barnName));
            }
            else{
                System.out.println("There is no such barn.");
            }
        }
        else{
            System.out.println("No barn to remove animals from.");
        }
        
    }
    
    private void menu_show_5_OldestAnimals(Farm farm){
        if (farm.getBarnsAmount() > 0){
            
            ArrayList<Animal> oldest = new ArrayList<>();
            
            for (Barn barn : farm.getBarns()){
                oldest.addAll(barn.getAnimalsInBarn());
            }
            
            Collections.sort(oldest, new Comparator<Animal>() {
                @Override
                public int compare(Animal o1, Animal o2) {
                    return o2.getAgeInMonths()-o1.getAgeInMonths();
                }
            });
            
            if (oldest.size() > 0) {
                System.out.println("The oldest animals:\n");
                int end = (oldest.size() < 5 ) ? oldest.size() : 5;
                for (int i = 0; i < end; i++) {
                    System.out.println(oldest.remove(0).showAnimal());
                }
            }
            
        }
        else{
            System.out.println("Farmwithout barns!");
        }
    }
    private void menu_show_5_YoungestAnimals(Farm farm){
        if (farm.getBarnsAmount() > 0){
        
            ArrayList<Animal> youngest = new ArrayList<>();
        
            for (Barn barn : farm.getBarns()){
                youngest.addAll(barn.getAnimalsInBarn());
            }
        
            Collections.sort(youngest, new Comparator<Animal>() {
                @Override
                public int compare(Animal o1, Animal o2) {
                    return o1.getAgeInMonths()-o2.getAgeInMonths();
                }
            });
            
            if (youngest.size() > 0) {
                System.out.println("The youngest animals:\n");
                int end = (youngest.size() < 5 ) ? youngest.size() : 5;
                for (int i = 0; i < end; i++) {
                    System.out.println(youngest.remove(0).showAnimal());
                }
            }
        
        }
        else{
            System.out.println("Farm without barns!");
        }
    }
    
    private void menu_showTheMostNumerousAnimal(Farm farm){
        Map<String,Integer> animals = new HashMap<>();
        animals = farm.getAllAnimals();
        int max = 0;
        String result="";
        for (String key : animals.keySet()){
            if (animals.get(key) > max){
                max = animals.get(key);
                result = key;
            }
        }
        if (!result.equals("")){
            System.out.println("The most numerous species: " + result + ": " + max);
        }
        else{
            System.out.println("Mistake - there are no animals");
        }
    }
    private void menu_showBarnWithMaxAnimals(Farm farm){
        ArrayList<Barn> barns = farm.getBarns();
        if (barns.size() > 0){
            Barn result=null;
            int max=0;
            for (Barn barn : barns){
                if (barn.getAnimalsAmount() >= max){
                    max = barn.getAnimalsAmount();
                    result = barn;
                }
            }
            System.out.println("The barn with the most animals:\n" + result.toString());
        }
        else{
            System.out.println("No barns!");
        }
    }
    private void menu_showAllVaccinedAnimals(Farm farm){
        ArrayList<Animal> vaccined = new ArrayList<>();
        ArrayList<Barn> barns = farm.getBarns();
        for (Barn barn: barns){
            
            ArrayList<Animal> vacAnimals = barn.getAllVaccined();
            if (vacAnimals.size() > 0) {
                vaccined.addAll(vacAnimals);
            }
        }
        System.out.println("Vaccinated animals: " + vaccined.size());
        for (Animal animal: vaccined){
            System.out.println(animal.showAnimal());
        }
    }
}