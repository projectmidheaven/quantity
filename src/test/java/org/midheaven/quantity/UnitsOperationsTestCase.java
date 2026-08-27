package org.midheaven.quantity;

import org.junit.jupiter.api.Test;
import org.midheaven.math.Rational;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.midheaven.quantity.Prefix.centi;
import static org.midheaven.quantity.Prefix.deca;
import static org.midheaven.quantity.Prefix.hecto;
import static org.midheaven.quantity.Prefix.kilo;
import static org.midheaven.quantity.Prefix.mega;
import static org.midheaven.quantity.Prefix.milli;
import static org.midheaven.quantity.StandardUnits.AMPERE;
import static org.midheaven.quantity.StandardUnits.ARE;
import static org.midheaven.quantity.StandardUnits.BIT;
import static org.midheaven.quantity.StandardUnits.BYTE;
import static org.midheaven.quantity.StandardUnits.CELSIUS_DEGREE;
import static org.midheaven.quantity.StandardUnits.FARAD;
import static org.midheaven.quantity.StandardUnits.GRAM;
import static org.midheaven.quantity.StandardUnits.HENRY;
import static org.midheaven.quantity.StandardUnits.HERTZ;
import static org.midheaven.quantity.StandardUnits.KILOGRAM;
import static org.midheaven.quantity.StandardUnits.METER;
import static org.midheaven.quantity.StandardUnits.MOL;
import static org.midheaven.quantity.StandardUnits.ONE;
import static org.midheaven.quantity.StandardUnits.SECOND;
import static org.midheaven.quantity.StandardUnits.SQUARE_METER;
import static org.midheaven.quantity.StandardUnits.TESLA;
import static org.midheaven.quantity.StandardUnits.TON;
import static org.midheaven.quantity.StandardUnits.VOLT;
import static org.midheaven.quantity.StandardUnits.WATT;

public class UnitsOperationsTestCase {
    
  
    @Test
    public void unitsCanBeCombined(){
        
        assertEquals(SECOND.times(SECOND).times(SECOND) , SECOND.raise(3));
        assertEquals(SECOND.raise(3), SECOND.times(SECOND).times(SECOND));

        assertEquals(ONE.over(SECOND).over(SECOND).over(SECOND) , SECOND.raise(-3));
        assertEquals(SECOND.raise(-3), ONE.over(SECOND).over(SECOND).over(SECOND));
        
        // VOLT == VOLT
        assertEquals(WATT.over(AMPERE) , KILOGRAM.times(SQUARE_METER).over(SECOND.raise(3)).over(AMPERE));
        // WATT x dimensionless = WATT
        assertEquals(WATT.over(ONE) , KILOGRAM.times(SQUARE_METER).over(SECOND.raise(3)).over(ONE));
        
        
        var epsilonUnit = FARAD.over(METER);
        var miuUnit = HENRY.over(METER);
        var cSquaredUnit =  ONE.over(epsilonUnit.times(miuUnit));
        
        assertEquals(METER.over(SECOND).raise(2), cSquaredUnit);
    }
    
    @Test
    public void unitsSymbolIsConsistent(){
        assertEquals("kg", KILOGRAM.symbol());
        assertEquals("kg m^2", KILOGRAM.times(SQUARE_METER).symbol());
        assertEquals("V", VOLT.symbol());
        assertEquals("V", WATT.over(AMPERE).symbol());
        assertEquals("V", KILOGRAM.times(SQUARE_METER).over(SECOND.raise(3)).over(AMPERE).symbol());
        assertEquals("A", WATT.over(VOLT).symbol());
        assertEquals("W", VOLT.times(AMPERE).symbol());
        
        var unit = KILOGRAM.over(AMPERE);
        UnitsSymbolsRegister.register(unit, "X");
        
        assertEquals("X", unit.symbol());
    }
    
    @Test
    public void unitsPrefixSymbolIsConsistent(){
        assertEquals("kg", kilo(GRAM).symbol());
        assertEquals("hg", Prefix.hecto(GRAM).symbol());
        assertEquals("t", Prefix.mega(GRAM).symbol());
        assertEquals("km", kilo(METER).symbol());
    }
    
    @Test
    public void unitsPrefixArithmetic(){
         var a = kilo(GRAM).of(2);
         var b = GRAM.of(2);
         var c = milli(GRAM).of(6);
        
        assertEquals(kilo(GRAM).of(4), a.plus(a));
        assertEquals(GRAM.of(4), b.plus(b));
        assertEquals(milli(GRAM).of(12), c.plus(c));
        
        assertEquals(GRAM.of(2002), b.plus(a));
        assertEquals(kilo(GRAM).of(Rational.of(2002, 1000)), a.plus(b));
        
        assertEquals(GRAM.of(Rational.of(2006, 1000)), b.plus(c));
        assertEquals(milli(GRAM).of(Rational.of(2006)), c.plus(b));
        
    }
    
    @Test
    public void unitParse(){
        assertEquals(centi(METER),  Unit.parse( "cm"));
        assertEquals(deca(METER),  Unit.parse( "dam"));
        assertEquals(milli(SECOND),  Unit.parse( "ms"));
        assertEquals(mega(MOL),  Unit.parse( "Mmol"));
        assertEquals(mega(GRAM),  Unit.parse( "t"));
        assertEquals(TESLA,  Unit.parse( "T"));
        assertEquals(VOLT,  Unit.parse( "V"));
        assertEquals(METER,  Unit.parse( "m"));
 
        assertEquals(kilo(GRAM),  Unit.parse( "kg"));
        assertEquals(ARE,  Unit.parse( "a"));
        assertEquals(hecto(ARE),  Unit.parse( "ha"));
        
        assertEquals(HERTZ,  Unit.parse( "Hz"));
        assertEquals(mega(HERTZ),  Unit.parse( "MHz"));
        
        assertEquals(CELSIUS_DEGREE,  Unit.parse( "ºC"));
        
        assertEquals(BIT,  Unit.parse( "b"));
        assertEquals(BYTE,  Unit.parse( "B"));
        assertEquals(kilo(BYTE),  Unit.parse( "kB"));
        
        assertEquals(SQUARE_METER,  Unit.parse( "m^2"));
        assertEquals(METER.over(SECOND),  Unit.parse( "m / s"));
        assertEquals(METER.over(SECOND).over(KILOGRAM.raise(2)),  Unit.parse( "m / s kg^2"));
        assertEquals(METER.over(SECOND).over(KILOGRAM.raise(2)),  Unit.parse( "m s^-1 kg^-2"));
        
        assertEquals(VOLT.over(TON).over(hecto(ARE).raise(2)),  Unit.parse( "V t^-1 ha^-2"));
    }
    

}


