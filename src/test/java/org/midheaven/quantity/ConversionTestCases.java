package org.midheaven.quantity;

import org.junit.jupiter.api.Test;
import org.midheaven.math.Interval;
import org.midheaven.math.Rational;
import org.midheaven.quantity.measurables.Distance;
import org.midheaven.quantity.measurables.Temperature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.midheaven.quantity.Prefix.kilo;
import static org.midheaven.quantity.Prefix.mega;
import static org.midheaven.quantity.Prefix.milli;
import static org.midheaven.quantity.StandardUnits.BIT;
import static org.midheaven.quantity.StandardUnits.BYTE;
import static org.midheaven.quantity.StandardUnits.ONE;

public class ConversionTestCases {
    
    private final QuantityConversion conversion = new ActualQuantityConversion();
    
    @Test
    public void celsiusConversion(){
        
        var celsius = StandardUnits.CELSIUS_DEGREE.of(120);
        var fahrenheit =  StandardUnits.FAHRENHEIT_DEGREE.of(248);
        var kelvin =  StandardUnits.KELVIN.of(Rational.of(39315, 100));
        
        assertEquals(kelvin, conversion.convert(celsius, StandardUnits.KELVIN));
        assertEquals(fahrenheit, conversion.convert(celsius, StandardUnits.FAHRENHEIT_DEGREE));
        assertEquals(celsius, conversion.convert(fahrenheit, StandardUnits.CELSIUS_DEGREE));
        assertEquals(celsius, conversion.convert(kelvin, StandardUnits.CELSIUS_DEGREE));
        
        assertEquals(kelvin, conversion.convert(fahrenheit, StandardUnits.KELVIN));
        assertEquals(fahrenheit, conversion.convert(kelvin, StandardUnits.FAHRENHEIT_DEGREE));
    }
    
    @Test
    public void sameBaseConversion(){
        
        var kg = StandardUnits.KILOGRAM.of(20);
        var grams =  StandardUnits.GRAM.of(20_000);
        var milligrams =  milli(StandardUnits.GRAM).of(20_000_000);

        assertEquals(grams, conversion.convert(kg, StandardUnits.GRAM));
        assertEquals(kg, conversion.convert(grams, StandardUnits.KILOGRAM));

        assertEquals(grams, conversion.convert(milligrams, StandardUnits.GRAM));
        assertEquals(milligrams, conversion.convert(grams, milli(StandardUnits.GRAM)));

        assertEquals(kg, conversion.convert(milligrams, StandardUnits.KILOGRAM));
        assertEquals(milligrams, conversion.convert(kg, milli(StandardUnits.GRAM)));

        var kiloVolt = kilo(StandardUnits.VOLT).of(20);
        var volt =  StandardUnits.VOLT.of(20_000);
        var milliVolt =  milli(StandardUnits.VOLT).of(20_000_000);
        
        assertEquals(volt, conversion.convert(kiloVolt, StandardUnits.VOLT));
        assertEquals(kiloVolt, conversion.convert(volt, kilo(StandardUnits.VOLT)));
        
        assertEquals(volt, conversion.convert(milliVolt, StandardUnits.VOLT));
        assertEquals(milliVolt, conversion.convert(volt, milli(StandardUnits.VOLT)));
        
        assertEquals(kiloVolt, conversion.convert(milliVolt, kilo(StandardUnits.VOLT)));
        assertEquals(milliVolt, conversion.convert(kiloVolt,  milli(StandardUnits.VOLT)));
    }
    
    @Test
    public void intervalConversion(){
        Interval.ranging(Quantity.<Distance>domain())
                           .from(StandardUnits.METER.of(2)).inclusive()
                           .to(Prefix.kilo(StandardUnits.METER).of(2)).inclusive()
                    .contains(StandardUnits.METER.of(1_000));
        
        Interval.ranging(Quantity.<Temperature>domain(conversion))
                           .from(StandardUnits.CELSIUS_DEGREE.of(2)).inclusive()
                           .to(StandardUnits.FAHRENHEIT_DEGREE.of(2)).inclusive()
            .contains(StandardUnits.CELSIUS_DEGREE.of(4));
    }
    
    @Test
    public void bitByteConversion(){
        
        assertEquals(BYTE.of(1000), conversion.convert(BIT.of(8000), BYTE));
        assertEquals(Prefix.kilo(BYTE).of(3), conversion.convert(BYTE.of(3000), Prefix.kilo(BYTE)));
    }
    
    @Test
    public void currencyConversion(){
        var BRL = StandardUnits.ofCurrency("BRL");
        var USD = StandardUnits.ofCurrency("USD");
        
        assertThrows(Exception.class, () -> conversion.convert(USD.of(1000), BRL));
        
        assertEquals(USD.of(1005), USD.of(1000).plus(USD.of(5)));
        assertEquals(ONE.of(200), USD.of(1000).over(USD.of(5)));
        assertEquals(USD.raise(2).of(5000), USD.of(1000).times(USD.of(5)));
    }
    
    @Test
    public void frequencyConversion(){
        
        assertEquals(StandardUnits.HERTZ.of(Rational.of(20).invert()), StandardUnits.SECOND.of(20).invert());
        assertEquals(StandardUnits.HERTZ.times(StandardUnits.HENRY).of(Rational.of(10)), StandardUnits.HENRY.of(20).over(StandardUnits.SECOND.of(2)));
    }
    
    @Test
    public void humanization(){
        
        assertEquals(mega(BYTE).of(Rational.of(208117, 100000)), BIT.of(16649363).humanize());
        assertEquals(mega(BYTE).of(Rational.of(2)), mega(BIT).of(16).humanize());
        assertEquals(kilo(ONE).of(Rational.of(20)), ONE.of(20000).humanize());
        assertEquals(kilo(ONE).of(Rational.of(1654, 1000)), ONE.of(1654).humanize());
    }
}
