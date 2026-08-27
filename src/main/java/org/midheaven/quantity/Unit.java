package org.midheaven.quantity;


import org.midheaven.math.Rational;
import org.midheaven.quantity.measurables.Measurable;

public sealed interface Unit<M extends Measurable>
    permits BaseUnit , ComposedUnit, MultipleUnit {
    
    static <Q extends Measurable> Unit<Q> parse(String text) {
        var possibleUnit =  UnitsSymbolsRegister.instance().unitOf(text);
        if (possibleUnit.isPresent()){
            return (Unit<Q>) possibleUnit.get();
        }
        return  null;
    }
    
    static <M extends Measurable> Unit<M> simplify(Unit<M> unit) {
        if (unit instanceof ComposedUnit<M> composedUnit){
            return (Unit<M>) composedUnit.simplify();
        }
        return unit;
    }
    
    String symbol();
    Dimension<M> dimension();

    default <R extends Measurable, T extends Measurable> Unit<R> times(Unit<T> other) {
        return UnitOperations.times(this, other);
    }
    
    default <R extends Measurable, T extends Measurable> Unit<R> over(Unit<T> other){
        return UnitOperations.over(this, other);
    }
    
    default <R extends Measurable> Unit<R> raise(int exponent){
        return UnitOperations.raise(this, exponent);
    }
    
    
    default Quantity<M> zero(){
        return new RationalQuantity<>(Rational.ZERO, this);
    }
    
    default Quantity<M> of(Rational magnitude){
        return new RationalQuantity<>(magnitude, this);
    }
    
    default Quantity<M> of(long magnitude){
        return new RationalQuantity<>(Rational.of(magnitude), this);
    }
    
}
