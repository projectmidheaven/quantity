package org.midheaven.quantity;

import org.midheaven.math.Rational;
import org.midheaven.quantity.measurables.Area;
import org.midheaven.quantity.measurables.Currency;
import org.midheaven.quantity.measurables.DigitalInformation;
import org.midheaven.quantity.measurables.Distance;
import org.midheaven.quantity.measurables.ElectricCapacitance;
import org.midheaven.quantity.measurables.ElectricCurrent;
import org.midheaven.quantity.measurables.ElectricInductance;
import org.midheaven.quantity.measurables.ElectricResistance;
import org.midheaven.quantity.measurables.Energy;
import org.midheaven.quantity.measurables.Force;
import org.midheaven.quantity.measurables.LuminousIntensity;
import org.midheaven.quantity.measurables.MagneticFluxDensity;
import org.midheaven.quantity.measurables.Mass;
import org.midheaven.quantity.measurables.One;
import org.midheaven.quantity.measurables.Power;
import org.midheaven.quantity.measurables.Temperature;
import org.midheaven.quantity.measurables.Time;
import org.midheaven.quantity.measurables.TimeFrequency;
import org.midheaven.quantity.measurables.Voltage;
import org.midheaven.quantity.measurables.Volume;

public class StandardUnits {
    
    // Special cases
    public static Unit<One> ONE = new BaseUnit<>("", StandardDimensions.DIMENSIONLESS);
    
    // Fundamental Units
    public static Unit<Distance> METER = new BaseUnit<>("m", StandardDimensions.DISTANCE);
    public static Unit<Mass> GRAM = new BaseUnit<>("g", StandardDimensions.MASS);
    public static Unit<Mass> KILOGRAM = Prefix.kilo(GRAM);
    public static Unit<Time> SECOND = new BaseUnit<>("s", StandardDimensions.TIME);
    public static Unit<Temperature> KELVIN = new BaseUnit<>("K", StandardDimensions.TEMPERATURE);
    public static Unit<ElectricCurrent> AMPERE = new BaseUnit<>("A", StandardDimensions.ELECTRIC_CURRENT);
    public static Unit<AmountOfSubstance> MOL = new BaseUnit<>("mol", StandardDimensions.AMOUNT_OF_SUBSTANCE);
    public static Unit<LuminousIntensity> CANDELA = new BaseUnit<>("cd", StandardDimensions.LUMINOUS_INTENSITY);
    
    // Auxiliar Units
    public static Unit<DigitalInformation> BIT = new BaseUnit<>("b", StandardDimensions.DIGITAL_INFORMATION);
    /**
     * Decimal BYTE where kB = 1000 B
     */
    public static Unit<DigitalInformation> BYTE = new BaseUnit<>("B", StandardDimensions.DIGITAL_INFORMATION);
  
    // Derived Units
    public static Unit<Temperature> CELSIUS_DEGREE = new BaseUnit<>("ºC", StandardDimensions.TEMPERATURE);
    public static Unit<Temperature> FAHRENHEIT_DEGREE = new BaseUnit<>("ºF", StandardDimensions.TEMPERATURE);
    
    public static Unit<TimeFrequency> HERTZ = UnitsSymbolsRegister.register(SECOND.raise(-1), "Hz");
    public static Unit<Mass> TON = UnitsSymbolsRegister.register(Prefix.mega(GRAM), "t");
    public static Unit<Area> SQUARE_METER = UnitsSymbolsRegister.register(METER.times(METER), METER.times(METER).symbol());
    public static Unit<Volume> CUBIC_METER = METER.times(METER).times(METER);
    public static Unit<Force> NEWTON = UnitsSymbolsRegister.register(KILOGRAM.times(METER.over(SECOND).over(SECOND)), "N");
    public static Unit<Energy> JOULE =  UnitsSymbolsRegister.register(KILOGRAM.times(SQUARE_METER).over(SECOND).over(SECOND), "J");
    public static Unit<Power> WATT =  UnitsSymbolsRegister.register(JOULE.over(SECOND), "W");
    public static Unit<Voltage> VOLT =  UnitsSymbolsRegister.register(WATT.over(AMPERE), "V");
    public static Unit<ElectricResistance> OHM =  UnitsSymbolsRegister.register(VOLT.over(AMPERE), "Ω");
    public static Unit<ElectricCapacitance> COULOMB =  UnitsSymbolsRegister.register(AMPERE.over(SECOND), "C");
    public static Unit<ElectricCapacitance> FARAD =  UnitsSymbolsRegister.register(COULOMB.over(VOLT), "F");
    public static Unit<ElectricInductance> HENRY =  UnitsSymbolsRegister.register(SECOND.raise(2).over(FARAD), "H");
    public static Unit<MagneticFluxDensity> TESLA =  UnitsSymbolsRegister.register(KILOGRAM.over(AMPERE).over(SECOND.raise(2)), "T");
    
    public static Unit<Area> ARE = UnitsSymbolsRegister.register(new MultipleUnit<>("a", Rational.TEN.raisedTo(2), SQUARE_METER), "a");

    public static Unit<Currency> ofCurrency(String isoCode){
        return new BaseUnit<>(isoCode, StandardDimensions.CURRENCY);
    }
    
    private StandardUnits (){}
}
