package org.midheaven.quantity;

import org.midheaven.lang.ValueClass;
import org.midheaven.quantity.measurables.Measurable;

import java.util.stream.Collectors;

@ValueClass
record ComposedUnit<M extends Measurable> (Composition composition, Dimension<M> dimension) implements Unit<M> {
    
   Unit<?> simplify(){
       if (composition.terms().findAny().isEmpty()){
          return StandardUnits.ONE;
       }
       return this;
    }
    
    @Override
    public String symbol() {
        return UnitsSymbolsRegister.instance()
                   .symbolOf(this)
                   .orElseGet(() -> composition.terms()
                                        .map(t -> t.exponent() == 1 ? t.value().toString() : (t.value() + "^" + t.exponent()))
                                        .sorted()
                                        .collect(Collectors.joining(" "))
                   );
    }
    
    @Override
    public String toString(){
        return symbol();
    }
    
    @Override
    public boolean equals(Object other){
        return other instanceof ComposedUnit<?>(Composition c, Dimension<?> d)
                   && this.dimension.equals(d)
                   && this.composition.equals(c);
    }
    
    @Override
    public int hashCode(){
        return composition.hashCode();
    }
}
