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
}
