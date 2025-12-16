package org.midheaven.quantity;

import org.midheaven.quantity.measurables.Measurable;

public interface QuantityConverter {
    
    Unit<?> fromUnit();
    Unit<?> toUnit();
    
    QuantityConverter reverse();
    
    <E extends Measurable> Quantity<E> fowardConvert(Quantity<E> fromValue);
    <E extends Measurable> Quantity<E> reverseConvert(Quantity<E> toValue);
}
