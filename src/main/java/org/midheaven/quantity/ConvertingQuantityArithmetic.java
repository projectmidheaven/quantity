package org.midheaven.quantity;

import org.midheaven.math.Arithmetic;
import org.midheaven.quantity.measurables.Measurable;

final class ConvertingQuantityArithmetic<M extends Measurable> implements Arithmetic<Quantity<M>, Quantity<M>> {
    
    private final Unit<M> unit;
    private final QuantityConversion conversion;
    
    public ConvertingQuantityArithmetic(Unit<M> unit, QuantityConversion conversion) {
        this.unit = unit;
        this.conversion = conversion;
    }
    
    @Override
    public Quantity<M> zero() {
        return unit.zero();
    }
    
    @Override
    public Quantity<M> sum(Quantity<M> a, Quantity<M> b) {
        return a.plus(b, conversion);
    }
    
    @Override
    public Quantity<M> over(Quantity<M> value, long count) {
        return value.over(count);
    }
}
