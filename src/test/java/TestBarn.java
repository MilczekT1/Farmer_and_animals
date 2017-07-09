import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestBarn {
    private Barn barn;
    @After
    public void afterTest(){
        barn = null;
    }
    
    @Test
    public void test1ArgConstructor(){
        // Correct argument
        try {
            barn = new Barn("testName");
            Assert.assertEquals("name should be \"testName\"","testName",barn.getName());
            Assert.assertEquals("barnID should be -1",-1,barn.getBarnID());
            Assert.assertEquals("capacity should be -1",-1,barn.getCapacity());
            Assert.assertEquals("animalsAmount should be -1",-1,barn.getBarnID());
            Assert.assertEquals("barnID should be -1",-1,barn.getBarnID());
            Assert.assertNull("animalsInBarn should be null",barn.getAnimalsInBarn());
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException has been thrown");
        }
        
        // Incorrect argument
        try {
            barn = new Barn("");
            Assert.fail("IllegalArgumentException has not been thrown");
        } catch (Exception e) { ; }
        try {
            barn = new Barn(null);
            Assert.fail("IllegalArgumentException has not been thrown");
        } catch (Exception e) { ; }
    }
    
    @Test
    public void test2ArgConstructor(){
        // Correct arguments
        try {
            barn = new Barn("ChickenLand",500);
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException has been thrown");
        }
        
        //Incorrect argument
        try {
            barn = new Barn(null,500);
            Assert.fail("IllegalArgumentException has not been thrown");
        } catch (Exception e) { ; }
        try {
            barn = new Barn("",500);
            Assert.fail("IllegalArgumentException has not been thrown");
        } catch (Exception e) { ; }
    
        try {
            barn = new Barn("ChickenLand",0);
            Assert.fail("IllegalArgumentException has not been thrown");
        } catch (Exception e) { ; }
    
        try {
            barn = new Barn("ChickenLand",-1);
            Assert.fail("IllegalArgumentException has not been thrown");
        } catch (Exception e) { ; }
    }
    @Test
    public void testIsFull(){
        // Correct
        try {
            barn = new Barn("testBarn",2);
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException has been thrown");
        }
        Assert.assertFalse(barn.isFull());
        try {
            barn.addAnimal(new Animal());
        } catch (OutOfPlaceException e) {
            Assert.fail("OutOfPlaceException has been thrown");
        }
        Assert.assertFalse(barn.isFull());
        try {
            barn.addAnimal(new Animal());
        } catch (OutOfPlaceException e) {
            Assert.fail("OutOfPlaceException has been thrown");
        }
        Assert.assertTrue(barn.isFull());
    }
    @Test
    public void testAddAnimal(){
        try {
            barn = new Barn("testBarn",2);
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException has been thrown");
        }
    
        try {
            barn.addAnimal(new Animal());
            Assert.assertEquals(1, barn.getAnimalsAmount());
        } catch (OutOfPlaceException e) {
            Assert.fail("OutOfPlaceException has been thrown");
        }
        
        
        if (!barn.isFull()){
            try {
                barn.addAnimal(new Animal());
                barn.addAnimal(new Animal());
                Assert.fail("OutOfStockException has not been thrown");
            } catch (OutOfPlaceException e) { ; }
        } else{
            Assert.fail("Barn should be full");
        }
    }
    @Test
    public void testAddAnimals(){
        //fulfill barn
        try {
            barn = new Barn("testBarn",2);
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException has been thrown");
        }
        
        ArrayList<Animal> animals = new ArrayList<Animal>(){{
            add(new Animal());
            add(new Animal());
        }};
        try {
            barn.addAnimals(animals);
            Assert.assertEquals(2, barn.getAnimalsAmount());
            if (!barn.isFull()){
                Assert.fail("Barn should be full");
            }
        } catch (OutOfPlaceException e) {
            Assert.fail("OutOfPlaceException has been thrown");
        }
        
        //overfill barn
        try {
            barn = new Barn("testBarn",2);
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException has been thrown");
        }
        ArrayList<Animal> animals2 = new ArrayList<Animal>(){{
            add(new Animal());
            add(new Animal());
            add(new Animal());
        }};
        try {
            barn.addAnimals(animals2);
            Assert.fail("OutOfPlaceException has not been thrown");
        } catch (OutOfPlaceException e) { ; }
        
        //empty list
        try {
            barn = new Barn("testBarn",2);
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException has been thrown");
        }
        try {
            barn.addAnimals(Collections.emptyList());
            Assert.fail("OutOfPlaceException has not been thrown");
        } catch (OutOfPlaceException e) { ; }
        
        // null
        try {
            barn.addAnimals(null);
            Assert.fail("OutOfPlaceException has not been thrown");
        } catch (OutOfPlaceException e) { ; }
    }
    @Test
    public void testRemoveAnimal(){
        
        try {
            barn = new Barn("testBarn",3);
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException has been thrown");
        }
    
        ArrayList<Animal> animals = new ArrayList<Animal>(){{
            try {
                // All have different weightInKG
                add(new Animal("Chicken",2,"male", 2, false, 1, "testBarn"));
                add(new Animal("Chicken",2,"male", 3, false, 1, "testBarn"));
                add(new Animal("Chicken",2,"male", 4, false, 1, "testBarn"));
            } catch (Exception e) {
                Assert.fail("IllegalArgumentException has been thrown");
            }
        }};
        try {
            barn.addAnimals(animals);
            Assert.assertEquals(3,barn.getAnimalsAmount());
        } catch (OutOfPlaceException e) {
            Assert.fail("OutOfPlaceException has been thrown");
        }
        
        try {
            int size = barn.getAnimalsAmount();
            barn.removeAnimal(new Animal("Chicken",2,"male", 2, false, 1, "testBarn"));
            Assert.assertFalse(barn.getAnimalsInBarn().contains(new Animal("Chicken",2,"male", 2, false, 1, "testBarn")));
            Assert.assertEquals(size-1, barn.getAnimalsAmount());
        } catch (Exception e) {
            if (e instanceof  OutOfPlaceException)
                Assert.fail("OutOfPlaceException has been thrown");
            if (e instanceof  IllegalArgumentException)
                Assert.fail("IllegalArgumentException has been thrown");
        }
        // remove null
        try {
            barn.removeAnimal(null);
            Assert.fail("IllegalARgumentException has not been thrown");
        } catch (Exception e) { ; }
        // Animal() object should not be in Barn
        try {
            barn.removeAnimal(new Animal());
            Assert.fail("Exception has not been thrown");
        } catch (Exception e) { ; }
    }
    @Test
    public void testGetAllVaccined(){
        try {
            barn = new Barn("ChickenLand", 5);
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException has been thrown");
        }
        
        ArrayList<Animal> animals = new ArrayList<Animal>(){{
            try {
                add(new Animal("Chicken",2,"male", 2, false, 1, "testBarn"));
                add(new Animal("Chicken",2,"male", 2, false, 1, "testBarn"));
                add(new Animal("Chicken",2,"male", 2, true, 1, "testBarn"));
                add(new Animal("Chicken",2,"male", 2, true, 1, "testBarn"));
                add(new Animal("Chicken",2,"male", 2, true, 1, "testBarn"));
            } catch (Exception e) {
                Assert.fail("IllegalArgumentException has been thrown");
            }
        }};
        try {
            barn.addAnimals(animals);
            Assert.assertEquals(5,barn.getAnimalsAmount());
        } catch (OutOfPlaceException e) {
            Assert.fail("OutOfPlaceException has been thrown");
        }
        ArrayList<Animal> vaccinedAnimals = barn.getAllVaccined();
        Assert.assertEquals(3,vaccinedAnimals.size());
        Assert.assertTrue(barn.getAnimalsInBarn().containsAll(vaccinedAnimals));
    }
    @Test
    public void testGetSpeciesAmounts(){
        try {
            barn = new Barn("ChickenLand", 5);
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException has been thrown");
        }
    
        ArrayList<Animal> animals = new ArrayList<Animal>(){{
            try {
                add(new Animal("Chicken",2,"male", 2, false, 1, "testBarn"));
                add(new Animal("Pig",2,"male", 2, false, 1, "testBarn"));
                add(new Animal("Turtle",2,"male", 2, true, 1, "testBarn"));
                add(new Animal("Elephant",2,"male", 2, true, 1, "testBarn"));
                add(new Animal("Elephant",3,"male", 2, true, 1, "testBarn"));
            } catch (Exception e) {
                Assert.fail("IllegalArgumentException has been thrown");
            }
        }};
        try {
            barn.addAnimals(animals);
            Assert.assertEquals(5,barn.getAnimalsAmount());
        } catch (OutOfPlaceException e) {
            Assert.fail("OutOfPlaceException has been thrown");
        }
        Map<String,Integer> speciesAmounts = barn.getSpeciesAmounts();
        if (speciesAmounts.containsKey("Chicken") &&
                    speciesAmounts.containsKey("Pig") &&
                    speciesAmounts.containsKey("Turtle") &&
                    speciesAmounts.containsKey("Elephant")) {
            
            Assert.assertEquals(Integer.valueOf(1), speciesAmounts.get("Chicken"));
            Assert.assertEquals(Integer.valueOf(1), speciesAmounts.get("Turtle"));
            Assert.assertEquals(Integer.valueOf(1), speciesAmounts.get("Pig"));
            Assert.assertEquals(Integer.valueOf(2), speciesAmounts.get("Elephant"));
        }
    }
}
