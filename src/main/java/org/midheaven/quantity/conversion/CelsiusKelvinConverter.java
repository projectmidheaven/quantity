package org.midheaven.quantity.conversion;

import org.midheaven.math.Rational;
import org.midheaven.quantity.AbstractQuantityConverter;
import org.midheaven.quantity.StandardUnits;
import org.midheaven.quantity.measurables.Temperature;

public final class CelsiusKelvinConverter extends AbstractQuantityConverter<Temperature> {
    
    private static final Rational  OFFSET = Rational.of(27315, 100);
    
    public CelsiusKelvinConverter() {
        super(StandardUnits.CELSIUS_DEGREE, StandardUnits.KELVIN);
    }
 
    @Override
    protected Rational convertForward(Rational celsius) {
        // K = °C + 273.15
        return celsius.plus(OFFSET);
    }
    
    @Override
    protected Rational convertBackward(Rational kelvin) {
        // °C = K -  273.15
        return kelvin.minus(OFFSET);
    }
}
