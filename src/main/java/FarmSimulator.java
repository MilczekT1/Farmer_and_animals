import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

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
        farm = new Farm("Farma 1");
    }
    
    private void runInConsole(FarmSimulator simulator){
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        farm.loadFarmIfExists(farm);
        
        simulator.printMenu();
        do {
            Integer choice = getIntegerWithRegex(in,"Wybór: ","\\d*");
            
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
                    
                default :   System.out.println("Wybierz ponownie");               break;
            }
            
            printAnimalSummary(farm);
        }while(true);
    }
    
    private String getString(BufferedReader in){
        try {
            return in.readLine();
        }
        catch(IOException exc){
            System.out.println("Bledne dane, sprobuj ponownie");
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
                System.out.println("puste");
            }
        }
        System.out.println("---------");
    }
    private void printMenu(){
        System.out.println("Menu");
        System.out.println("0 - Koniec");
        System.out.println("1 - Dodaj stodolę");
        System.out.println("2 - Usuń stodole");
        System.out.println("3 - Dodaj zwierzę do stodoly");
        System.out.println("4 - Usuń zwierzę ze stodoly");
        System.out.println("5 - Pokaz 5 najstarszych zwierząt");
        System.out.println("6 - Pokaz 5 najmlodszych zwierząt");
        System.out.println("7 - Pokaz najliczniejszy gatunek");
        System.out.println("8 - Pokaz Stodole z najwieksza iloscia zwierzat");
        System.out.println("9 - Pokaż wszystkie zaszczepione zwierzęta");
    }
    private void printAnimalSummary(Farm farm){
        Map<String, Integer> allAnimals = farm.getAllAnimals();
        if (allAnimals.size() > 0) {
            System.out.println("\n" + allAnimals);
        }
        else{
            System.out.println("Liczba zwierzat: 0");
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
        String barnName = getStringWithRegex(inL,"Nazwa stodoly: ","\\D*\\d{0,4}");
        Integer capacity = getIntegerWithRegex(inL,"Pojemnosc stodoly: ","\\d{1,}");
        farm.addBarn(new Barn(barnName, capacity));
    }
    private void menu_removeBarnFromFarm(Farm farm, BufferedReader inL){
        String barnName = getStringWithRegex(inL,"Nazwa stodoly: ","\\D*\\d{0,4}");
        Integer capacity = getIntegerWithRegex(inL,"Pojemnosc stodoly: ","\\d{1,}");
        farm.removeBarn(barnName, capacity);
    }
    
    private void menu_addAnimalToBarn(Farm farm, BufferedReader inL){
        if (farm.getBarnsAmount() > 0) {
            printAllBarns(farm.getBarns());
    
            String species = getStringWithRegex(inL,"Gatunek: ","\\D*").toLowerCase();
            Integer ageInMonths = getIntegerWithRegex(inL,"Wiek a miesiacach: ","\\d{1,3}");
            Integer weight = getIntegerWithRegex(inL,"Waga (Kg): ","\\d{1,4}");
            String barnName = getStringWithRegex(inL,"Do jakiej stodoly: ","\\D*\\d{0,4}");
    
            Integer genderINT = getIntegerWithRegex(inL,"Plec (0-samica 1-samiec): ","[0-1]");
            String gender = (genderINT.equals(0)) ? "samica" : "samiec";
            
            Integer vaccineINT = getIntegerWithRegex(inL,"Czy szczepiony (0-false 1-true): ","[0-1]");
            boolean vaccined = (vaccineINT.equals(0)) ? false : true;

            ArrayList<Barn> barns = farm.getBarns();
            Barn tempBarn = new Barn(barnName);
            if (barns.contains(tempBarn)) {
                int index = barns.indexOf(tempBarn);
                int barnID = barns.get(index).getBarnID();
                barns.get(index).addAnimal(new Animal(species, ageInMonths, gender, weight, vaccined, barnID));
            }
            else{
                System.out.println("Brak stodoly, nie mozna umiescic zwierzecia.");
            }
        }else{
            System.out.println("Brak stodoly, nie mozna umiescic zwierzecia.");
        }
    }
    private void menu_removeAnimalFromBarn(Farm farm, BufferedReader inL){
        if (farm.getBarnsAmount() >0){
            printAllBarns(farm.getBarns());
    
            String species = getStringWithRegex(inL,"Gatunek: ","\\D*").toLowerCase();
            Integer ageInMonths = getIntegerWithRegex(inL,"Wiek a miesiacach: ","\\d{1,3}");
            Integer weight = getIntegerWithRegex(inL,"Waga (Kg): ","\\d{1,4}");
            String barnName = getStringWithRegex(inL,"Do jakiej stodoly: ","\\D*\\d{0,4}");
    
            Integer genderINT = getIntegerWithRegex(inL,"Plec (0-samica 1-samiec): ","[0-1]");
            String gender = (genderINT.equals(0)) ? "samica" : "samiec";
    
            Integer vaccineINT = getIntegerWithRegex(inL,"Czy szczepiony (0-false 1-true): ","[0-1]");
            boolean vaccined = (vaccineINT.equals(0)) ? false : true;
            
            ArrayList<Barn> barns = farm.getBarns();
            Barn tempBarn = new Barn(barnName);
            if(farm.getBarns().contains(new Barn(barnName)))
            if (barns.contains(tempBarn)) {
                int index = barns.indexOf(tempBarn);
                int barnID = barns.get(index).getBarnID();
                barns.get(index).removeAnimal(new Animal(species, ageInMonths, gender, weight, vaccined, barnID));
            }
            else{
                System.out.println("Brak stodoly z której można by usunąć zwierzę.");
            }
        }
        else{
            System.out.println("Brak stodoly z której można by usunąć zwierzę.");
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
                System.out.println("Najstarsze zwierzeta:\n");
                int end = (oldest.size() < 5 ) ? oldest.size() : 5;
                for (int i = 0; i < end; i++) {
                    System.out.println(oldest.remove(0).toString());
                }
            }
            
        }
        else{
            System.out.println("Farma bez stodol!");
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
                System.out.println("Najmlodsze zwierzeta:\n");
                int end = (youngest.size() < 5 ) ? youngest.size() : 5;
                for (int i = 0; i < end; i++) {
                    System.out.println(youngest.remove(0).toString());
                }
            }
        
        }
        else{
            System.out.println("Farma bez stodol!");
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
            System.out.println("Najliczniejszy gatunek: " + result + ": " + max);
        }
        else{
            System.out.println("Blad - brak zwierzat");
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
            System.out.println("Stodola z najwieksza iloscia zwierząt:\n" + result.toString());
        }
        else{
            System.out.println("brak stodol");
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
        System.out.println("zaszczepionych zwierzat: " + vaccined.size());
        for (Animal animal: vaccined){
            System.out.println(animal.toString());
        }
    }
}