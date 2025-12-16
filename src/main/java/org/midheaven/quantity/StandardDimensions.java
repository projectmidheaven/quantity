package org.midheaven.quantity;


import org.midheaven.quantity.measurables.Acceleration;
import org.midheaven.quantity.measurables.Area;
import org.midheaven.quantity.measurables.Distance;
import org.midheaven.quantity.measurables.ElectricCurrent;
import org.midheaven.quantity.measurables.Energy;
import org.midheaven.quantity.measurables.Force;
import org.midheaven.quantity.measurables.LuminousIntensity;
import org.midheaven.quantity.measurables.MagneticFluxDensity;
import org.midheaven.quantity.measurables.Mass;
import org.midheaven.quantity.measurables.One;
import org.midheaven.quantity.measurables.Power;
import org.midheaven.quantity.measurables.Temperature;
import org.midheaven.quantity.measurables.Time;
import org.midheaven.quantity.measurables.Velocity;
import org.midheaven.quantity.measurables.Voltage;
import org.midheaven.quantity.measurables.Volume;

public class StandardDimensions {
    
    // Special cases
    public static Dimension<One> DIMENSIONLESS = BaseDimension.of("");
 
    // Fundamental Dimensions
    public static Dimension<Distance> DISTANCE = BaseDimension.of("L");
    public static Dimension<Mass> MASS = BaseDimension.of("M");
    public static Dimension<Time> TIME = BaseDimension.of("T");
    public static Dimension<Temperature> TEMPERATURE = BaseDimension.of("θ");
    public static Dimension<ElectricCurrent> ELECTRIC_CURRENT = BaseDimension.of("I");
    public static Dimension<AmountOfSubstance> AMOUNT_OF_SUBSTANCE = BaseDimension.of("N");
    public static Dimension<LuminousIntensity> LUMINOUS_INTENSITY = BaseDimension.of("J");
    
    // Derived Dimensions
    public static Dimension<Area> AREA = DISTANCE.times(DISTANCE);
    public static Dimension<Volume> VOLUME = DISTANCE.times(DISTANCE).times(DISTANCE);
    public static Dimension<Velocity> VELOCITY = DISTANCE.over(TIME);
    public static Dimension<Acceleration> ACCELERATION = VELOCITY.over(TIME);
    public static Dimension<Force> FORCE = MASS.times(ACCELERATION);
    public static Dimension<Energy> ENERGY = MASS.times(AREA).over(TIME).over(TIME);
    public static Dimension<Power> POWER = ENERGY.over(TIME);
    public static Dimension<Voltage> VOLTAGE = POWER.over(ELECTRIC_CURRENT);
    public static Dimension<MagneticFluxDensity> MAGNETIC_FLUX_DENSITY = MASS.over(ELECTRIC_CURRENT).over(TIME.raise(2));
    
    private StandardDimensions(){}
}
