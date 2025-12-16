package org.midheaven.quantity;

import org.midheaven.math.Rational;
import org.midheaven.quantity.measurables.Measurable;

public final class ScaleConverter implements QuantityConverter {
    
    private final Unit<?> fromUnit;
    private final Unit<?> toUnit;
    private final Rational factor;
    
    public ScaleConverter (Unit<?> fromUnit, Unit<?> toUnit, Rational factor){
        this.fromUnit = fromUnit;
        this.toUnit = toUnit;
        this.factor = factor;
    }
    
    @Override
    public Unit<?> fromUnit() {
        return fromUnit;
    }
    
    @Override
    public Unit<?> toUnit() {
        return toUnit;
    }
    
    @Override
    public QuantityConverter reverse() {
        return new ReversedQuantityConverter(this);
    }
    
    @Override
    public <E extends Measurable> Quantity<E> fowardConvert(Quantity<E> fromValue) {
        return (Quantity<E>) toUnit().of(fromValue.magnitude().times(factor));
    }
    
    @Override
    public <E extends Measurable> Quantity<E> reverseConvert(Quantity<E> toValue) {
        return (Quantity<E>) fromUnit().of(toValue.magnitude().over(factor));
    }
}
