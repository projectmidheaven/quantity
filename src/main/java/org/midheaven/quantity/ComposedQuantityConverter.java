package org.midheaven.quantity;

import org.midheaven.quantity.measurables.Measurable;

import java.util.LinkedList;

public class ComposedQuantityConverter implements QuantityConverter{
    
    LinkedList<QuantityConverter> converters = new LinkedList<>();
    
    public ComposedQuantityConverter(QuantityConverter a, QuantityConverter b) {
        converters.add(a);
        converters.add(b);
    }
    
    @Override
    public Unit<?> fromUnit() {
        return converters.getFirst().fromUnit();
    }
    
    @Override
    public Unit<?> toUnit() {
        return converters.getLast().toUnit();
    }
    
    @Override
    public QuantityConverter reverse() {
        return new ReversedQuantityConverter(this);
    }
    
    @Override
    public <E extends Measurable> Quantity<E> fowardConvert(Quantity<E> fromValue) {
        var value = fromValue;
        for (var converter: converters){
            value = converter.fowardConvert(value);
        }
        return value;
    }
    
    @Override
    public <E extends Measurable> Quantity<E> reverseConvert(Quantity<E> toValue) {
        var value = toValue;
        var reverseIterator =  converters.listIterator(converters.size());
        
        while(reverseIterator.hasPrevious()){
            value = reverseIterator.previous().reverseConvert(value);
        }
      
        return value;
    }
}
