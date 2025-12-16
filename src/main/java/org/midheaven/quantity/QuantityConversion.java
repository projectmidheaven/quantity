package org.midheaven.quantity;

import org.midheaven.quantity.measurables.Measurable;

public interface QuantityConversion {
    
    <M extends Measurable> Quantity<M> convert(Quantity<M> original, Unit<M> targetUnit);
}
