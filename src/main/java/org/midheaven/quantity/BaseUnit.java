package org.midheaven.quantity;

import org.midheaven.lang.ValueClass;
import org.midheaven.quantity.measurables.Measurable;

@ValueClass
record BaseUnit<M extends Measurable> (String symbol, Dimension<M> dimension) implements Unit<M> , Composition.Composable {
    
    @Override
    public String toString(){
        return symbol;
    }
    
    @Override
    public boolean equals(Object other){
       return other instanceof BaseUnit<?>(String s, Dimension<?> d)
           && this.dimension.equals(d)
           && this.symbol.equals(s);
    }
    
    @Override
    public boolean isIdentity() {
        return this.symbol.isEmpty();
    }
}
