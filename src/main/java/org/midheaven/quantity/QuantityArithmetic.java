package org.midheaven.quantity;

import org.midheaven.math.Arithmetic;
import org.midheaven.quantity.measurables.Measurable;

final class QuantityArithmetic<M extends Measurable> implements Arithmetic<Quantity<M>, Quantity<M>> {
    
    private final Unit<M> unit;
    
    public QuantityArithmetic(Unit<M> unit) {
        this.unit = unit;
    }
    
    @Override
    public Quantity<M> zero() {
        return unit.zero();
    }
    
    @Override
    public Quantity<M> sum(Quantity<M> a, Quantity<M> b) {
        return a.plus(b);
    }
    
    @Override
    public Quantity<M> over(Quantity<M> value, long count) {
        return value.over(count);
    }
}
