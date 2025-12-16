package org.midheaven.quantity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CombinationTestCase {
    
    @Test
    public void combinationMultiplication(){
        
        var a = new Composition((Composition.Composable) StandardDimensions.DISTANCE);
        var b = new Composition((Composition.Composable) StandardDimensions.TIME);
        var c = a.times(b);
        
        assertEquals(2, c.terms().count());
        
        var d = c.times(new Composition((Composition.Composable) StandardDimensions.DIMENSIONLESS));
        
        assertEquals(2, d.terms().count());
    }
    
    @Test
    public void combinationDivision(){
        
        var a = new Composition((Composition.Composable) StandardDimensions.DISTANCE);
        var b = new Composition((Composition.Composable) StandardDimensions.TIME);
        var c = a.over(b);
        
        assertEquals(2, c.terms().count());
        
        var d = c.over(new Composition((Composition.Composable) StandardDimensions.DIMENSIONLESS));
        
        assertEquals(2, d.terms().count());
    }
}
