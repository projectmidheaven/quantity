package org.midheaven.quantity.conversion;

import org.midheaven.math.Rational;
import org.midheaven.quantity.AbstractQuantityConverter;
import org.midheaven.quantity.StandardUnits;
import org.midheaven.quantity.measurables.Temperature;

public final class CelsiusFahrenheitConverter extends AbstractQuantityConverter<Temperature> {
    
    private static final int ZERO_REFERENCE = 32;
    private static final Rational REDUCTION_FACTOR = (Rational.of(9,5));
    
    public CelsiusFahrenheitConverter() {
        super(StandardUnits.CELSIUS_DEGREE, StandardUnits.FAHRENHEIT_DEGREE);
    }
 
    @Override
    protected Rational convertForward(Rational celsius) {
        // F = C x 9/5 + 32
        return celsius.times(REDUCTION_FACTOR).plus(ZERO_REFERENCE);
    }
    
    @Override
    protected Rational convertBackward(Rational fahrenheit) {
        // C = (F - 32) x 5/9  <=> C = (F - 32) ÷ 9/5
        return fahrenheit.minus(ZERO_REFERENCE).over(REDUCTION_FACTOR);
    }
}
