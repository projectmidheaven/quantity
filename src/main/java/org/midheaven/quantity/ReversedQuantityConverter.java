package org.midheaven.quantity;

import org.midheaven.quantity.measurables.Measurable;

final class ReversedQuantityConverter implements QuantityConverter {
    
    QuantityConverter original;
    
    ReversedQuantityConverter(QuantityConverter original) {
        this.original = original;
    }
    
    @Override
    public Unit<?> fromUnit() {
        return original.toUnit();
    }
    
    @Override
    public Unit<?> toUnit() {
        return original.fromUnit();
    }
    
    @Override
    public QuantityConverter reverse() {
        return original;
    }
    
    @Override
    public <E extends Measurable> Quantity<E> fowardConvert(Quantity<E> fromValue) {
        return original.reverseConvert(fromValue);
    }
    
    @Override
    public <E extends Measurable> Quantity<E> reverseConvert(Quantity<E> toValue) {
        return original.fowardConvert(toValue);
    }
    
    
}
