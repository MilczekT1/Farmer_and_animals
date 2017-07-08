import org.junit.Assert;
import org.junit.Test;

public class TestBarn {
    private Barn barn;
    
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
        barn.addAnimal(new Animal());
        Assert.assertFalse(barn.isFull());
        barn.addAnimal(new Animal());
        Assert.assertTrue(barn.isFull());
    }
}
