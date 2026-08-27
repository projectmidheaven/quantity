package org.midheaven.quantity;

import org.midheaven.lang.ValueClass;
import org.midheaven.quantity.measurables.Measurable;

@ValueClass
final class BaseUnit<M extends Measurable> implements Unit<M> , Composition.Composable {
   
    final String symbol;
    final Dimension<M> dimension;
    
    BaseUnit(String symbol, Dimension<M> dimension) {
        this.symbol = symbol;
        this.dimension = dimension;
        UnitsSymbolsRegister.register(this, symbol);
    }
    
    @Override
    public String toString(){
        return symbol;
    }
    
    @Override
    public boolean equals(Object other){
       return other instanceof BaseUnit<?> that
           && this.dimension.equals(that.dimension)
           && this.symbol.equals(that.symbol);
    }
    
    @Override
    public boolean isIdentity() {
        return this.symbol.isEmpty();
    }
    
    @Override
    public String symbol() {
        return symbol;
    }
    
    @Override
    public Dimension<M> dimension() {
        return dimension;
    }
}
