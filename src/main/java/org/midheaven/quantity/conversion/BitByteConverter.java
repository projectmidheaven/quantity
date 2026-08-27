package org.midheaven.quantity.conversion;

import org.midheaven.math.Rational;
import org.midheaven.quantity.AbstractQuantityConverter;
import org.midheaven.quantity.StandardUnits;
import org.midheaven.quantity.measurables.DigitalInformation;

public final class BitByteConverter extends AbstractQuantityConverter<DigitalInformation> {
    
    public BitByteConverter() {
        super(StandardUnits.BIT, StandardUnits.BYTE);
    }
 
    @Override
    protected Rational convertForward(Rational bits) {
        return bits.over(8);
    }
    
    @Override
    protected Rational convertBackward(Rational bytes) {
        return bytes.times(8);
    }
}
