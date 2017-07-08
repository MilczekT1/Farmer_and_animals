import org.junit.Assert;
import org.junit.Test;

public class TestAnimal {
    private static Animal animal;

    @Test
    public void testNoArgConstructor(){
        animal = new Animal();
        Assert.assertNull("species should be null",animal.getSpecies());
        Assert.assertNull("gender should be null",animal.getGender());
        Assert.assertNull("barnName should be null",animal.getBarnName());
        Assert.assertEquals("ageInMonths should be -1",-1, animal.getAgeInMonths());
        Assert.assertEquals("weightInKG should be -1",-1, animal.getWeightInKG());
        Assert.assertEquals("barnID should be -1",-1,animal.getBarnID());
        Assert.assertFalse("isVaccinated should be false",animal.isVaccinated());
    }
    @Test
    public void testFullArgConstructor(){
        // All correct arguments
        try {
            animal = new Animal("Chicken",2,"male",1,false, 5,"testBarn");
            Assert.assertEquals("species should be \"Chicken\"","Chicken",animal.getSpecies());
            Assert.assertEquals("ageInMonths should be 2",2,animal.getAgeInMonths());
            Assert.assertEquals("gender should be \"male\"","male",animal.getGender());
            Assert.assertEquals("weightInKG should be 1",1,animal.getWeightInKG());
            Assert.assertFalse("isVaccinated should be false",animal.isVaccinated());
            Assert.assertEquals("barnID should be 5",5,animal.getBarnID());
            Assert.assertEquals("barnName should be \"testBarn\"","testBarn",animal.getBarnName());
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException has been thrown");
        }
        
        // One argument incorrect
        try {
            animal = new Animal(null,2,"male",1,false, 5,"testBarn");
            Assert.fail("IllegalArgumentException has not been thrown");
        } catch (Exception e) { ; }
        
        try {
            animal = new Animal("",2,"male",1,false, 5,"testBarn");
            Assert.fail("IllegalArgumentException has not been thrown");
        } catch (Exception e) { ; }
        
        try {
            animal = new Animal("Chicken",0,"male",1,false, 5,"testBarn");
            Assert.fail("IllegalArgumentException has not been thrown");
        } catch (Exception e) { ; }
        
        try {
            animal = new Animal("Chicken",2,null,1,false, 5,"testBarn");
            Assert.fail("IllegalArgumentException has not been thrown");
        } catch (Exception e) { ; }
        
        try {
            animal = new Animal("Chicken",2,"fglkshjg",1,false, 5,"testBarn");
            Assert.fail("IllegalArgumentException has not been thrown");
        } catch (Exception e) { ; }
        
        try {
            animal = new Animal("Chicken",2,"male",0,false, 5,"testBarn");
            Assert.fail("IllegalArgumentException has not been thrown");
        } catch (Exception e) { ; }
        
        try {
            animal = new Animal("Chicken",2,"male",1,false, 0,"testBarn");
            Assert.fail("IllegalArgumentException has not been thrown");
        } catch (Exception e) { ; }
        
        try {
            animal = new Animal("Chicken",2,"male",1,false, 5,"");
            Assert.fail("IllegalArgumentException has not been thrown");
        } catch (Exception e) { ; }
        
        try {
            animal = new Animal("Chicken",2,"male",1,false, 5,null);
            Assert.fail("IllegalArgumentException has not been thrown");
        } catch (Exception e) { ; }
    }
}
