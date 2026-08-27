package org.midheaven.quantity;

import org.midheaven.lang.ValueClass;
import org.midheaven.math.Rational;
import org.midheaven.quantity.measurables.Measurable;

@ValueClass
record MultipleUnit<M extends Measurable>(String msymbol, Rational factor, Unit<M> referenceUnit) implements Unit<M> , Composition.Composable {
    
    @Override
    public Dimension<M> dimension() {
        return referenceUnit.dimension();
    }
    
   
    @Override
    public boolean equals(Object other){
        return other instanceof MultipleUnit<?>(String s, Rational f, Unit<?> u)
                   && this.msymbol.equals(s)
                   && this.factor.equals(f)
                   && this.referenceUnit.equals(u);
    }
    
    @Override
    public int hashCode(){
        return referenceUnit.hashCode();
    }
    
    @Override
    public String symbol(){
        return UnitsSymbolsRegister.instance()
                   .symbolOf(this)
                   .orElse(msymbol);
    }
    @Override
    public String toString(){
        return symbol();
    }
    
    @Override
    public boolean isIdentity() {
        return false;
    }
}
