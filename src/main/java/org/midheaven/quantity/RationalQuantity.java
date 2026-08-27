package org.midheaven.quantity;

import org.midheaven.lang.ValueClass;
import org.midheaven.math.Rational;
import org.midheaven.quantity.measurables.Measurable;

@ValueClass
record RationalQuantity<M extends Measurable>(Rational magnitude, Unit<M> unit) implements Quantity<M> {
    
    @Override
    public int compareTo(Quantity<M> other) {
        return this.magnitude.compareTo(checkSameUnit(this, other).magnitude());
    }
    
    @Override
    public int compareTo(Quantity<M> other, QuantityConversion conversion) {
        return this.magnitude.compareTo(conversion.convert(other, this.unit).magnitude());
    }
    
    @Override
    public int sign() {
        return magnitude().sign();
    }
    
    @Override
    public boolean isZero() {
        return magnitude.isZero();
    }
    
    @Override
    public Quantity<M> negate() {
        return new RationalQuantity<>(this.magnitude().negate(), this.unit());
    }
    
    @Override
    public Quantity<M> minus(Quantity<M> other) {
        return new RationalQuantity<>(this.magnitude().minus(checkSameUnit(this, other).magnitude()), this.unit());
    }
    
    @Override
    public Quantity<M> plus(Quantity<M> other) {
        return new RationalQuantity<>(this.magnitude().plus(checkSameUnit(this, other).magnitude()), this.unit());
    }
    
    private Quantity<M> checkSameUnit(Quantity<M> a, Quantity<M> b){
        if (!a.unit().equals(b.unit())) {
            if (a.unit() instanceof BaseUnit<M> aBase
                    && b.unit() instanceof MultipleUnit<M> multiple
                    && multiple.referenceUnit().equals(aBase)){
                return b.times(multiple.factor());
            } else if (b.unit() instanceof BaseUnit<M> aBase
                 && a.unit() instanceof MultipleUnit<M> multiple
                 && multiple.referenceUnit().equals(aBase)){
                return b.over(multiple.factor());
            }
            throw new IncompatibleUnitException();
        }
        
        return b;
    }
    
    @Override
    public Quantity<M> minus(Quantity<M> other, QuantityConversion conversion) {
        var converted = conversion.convert(other, this.unit);
        return new RationalQuantity<>(this.magnitude().minus(converted.magnitude()), this.unit());
    }
    
    @Override
    public Quantity<M> plus(Quantity<M> other, QuantityConversion conversion) {
        var converted = conversion.convert(other, this.unit);
        return new RationalQuantity<>(this.magnitude().plus(converted.magnitude()), this.unit());
    }
    
    @Override
    public Quantity<M> abs() {
        return new RationalQuantity<>(this.magnitude().abs(), this.unit());
    }
    
    @Override
    public Quantity<M> times(Rational factor) {
        return new RationalQuantity<>(this.magnitude().times(factor), this.unit());
    }
    
    @Override
    public Quantity<M> over(Rational factor) {
        return new RationalQuantity<>(this.magnitude().over(factor), this.unit());
    }
    
    @Override
    public String toString(){
        return magnitude + " " + unit.symbol();
    }
    
    @Override
    public boolean equals(Object other){
        return other instanceof  Quantity that
            && this.magnitude.equals(that.magnitude())
            && this.unit.equals(that.unit());
    }
    
    @Override
    public int hashCode(){
        return this.magnitude.hashCode();
    }
    
    @Override
    public Quantity<M> invert() {
        return new RationalQuantity<>(magnitude.invert(), unit.raise(-1));
    }
    
    @Override
    public Quantity<M> over(Quantity<M> other) {
        return new RationalQuantity<>(magnitude.over(other.magnitude()),Unit.simplify(unit.over(other.unit())));
    }
    
    @Override
    public boolean isOne() {
        return false;
    }
    
    @Override
    public Quantity<M> times(Quantity<M> other) {
        return new RationalQuantity<>(magnitude.times(other.magnitude()), unit.times(other.unit()));
    }
    
}
