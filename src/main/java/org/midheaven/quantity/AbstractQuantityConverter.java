package org.midheaven.quantity;

import org.midheaven.math.Rational;
import org.midheaven.quantity.measurables.Measurable;

@SuppressWarnings("unchecked")
public abstract class AbstractQuantityConverter<M extends Measurable> implements QuantityConverter{
    
    private final Unit<M> fromUnit;
    private final Unit<M> toUnit;
    
    protected AbstractQuantityConverter(
        Unit<M> fromUnit,
        Unit<M> toUnit
    ){
        this.fromUnit = fromUnit;
        this.toUnit = toUnit;
    }
    
    @Override
    public final Unit<M> fromUnit() {
        return fromUnit;
    }
    
    @Override
    public final Unit<M> toUnit() {
        return toUnit;
    }
    
    @Override
    public final QuantityConverter reverse() {
        return new ReversedQuantityConverter(this);
    }
    
    @Override
    public <E extends Measurable> Quantity<E> fowardConvert(Quantity<E> fromValue){
        if (!fromValue.unit().equals(fromUnit)){
            throw new IncompatibleUnitException();
        }
        return (Quantity<E>) toUnit().of(convertForward(fromValue.magnitude()));
    }
    
    protected abstract Rational convertForward(Rational fromValue);
    
    public <E extends Measurable> Quantity<E> reverseConvert(Quantity<E> toValue){
        if (!toValue.unit().equals(toUnit)){
            throw new IncompatibleUnitException();
        }
        return (Quantity<E>) fromUnit().of(convertBackward(toValue.magnitude()));
    }
    
    protected abstract Rational convertBackward(Rational toValue);
}


