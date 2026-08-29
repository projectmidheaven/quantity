package org.midheaven.quantity;

import org.midheaven.math.Rational;
import org.midheaven.quantity.conversion.BitByteConverter;
import org.midheaven.quantity.conversion.CelsiusFahrenheitConverter;
import org.midheaven.quantity.conversion.CelsiusKelvinConverter;
import org.midheaven.quantity.measurables.Measurable;

import java.util.HashMap;
import java.util.Map;

public class ActualQuantityConversion implements QuantityConversion{
    
    record KeyPair(Unit<?> fromUnit, Unit<?> toUnit){}
    
    private final Map<KeyPair, QuantityConverter> converters = new HashMap<>();
    
    public ActualQuantityConversion(){
        registerConverter(new CelsiusFahrenheitConverter());
        registerConverter(new CelsiusKelvinConverter());
        registerConverter(new BitByteConverter());
    }
    
    public ActualQuantityConversion registerConverter(QuantityConverter converter){
        converters.put(new KeyPair(converter.fromUnit(), converter.toUnit()), converter);
        converters.put(new KeyPair(converter.toUnit(), converter.fromUnit()), converter.reverse());
        return this;
    }
    
    @Override
    public <M extends Measurable> Quantity<M> convert(Quantity<M> original, Unit<M> targetUnit) {
        if (original == null){
            return null;
        }
        var converter = converters.get(new KeyPair(original.unit(), targetUnit));
        if (converter == null){
            var reverseConverter = converters.get(new KeyPair(targetUnit, original.unit()));
            if (reverseConverter == null){
                var x = resolveScaleFactor(original.unit());
                var y = resolveScaleFactor(targetUnit);
                // try by same base scaling
                if (x.unit.equals(y.unit)) {
                    var scaleFactor = x.factor.over(y.factor);
                    converter = new ScaleConverter(original.unit(), targetUnit, scaleFactor);
                } else {
                    // try transitive conversion U -> T <=> U -> M -> T
                    var startOptions = converters.keySet().stream().filter(it -> it.fromUnit.equals(original.unit())).toList();
                    var endOptions = converters.keySet().stream().filter(it -> it.toUnit.equals(targetUnit)).toList();
                    
                    outer: for ( var s : startOptions){
                        for ( var e : endOptions){
                            if (s.toUnit.equals(e.fromUnit)){
                                var a = converters.get(s);
                                var b = converters.get(e);
                                converter = new ComposedQuantityConverter(a, b);
                                break outer;
                            }
                        }
                    }
                    
                    if (converter == null && endOptions.size() == 1){
                        // the direct converter
                        var baseConverter = converters.get(endOptions.getFirst());
                        converter = new ComposedQuantityConverter(
                            new ScaleConverter(original.unit(), endOptions.getFirst().fromUnit, x.factor),
                            baseConverter
                        );
                    
                    }
                }
                
                if (converter == null){
                    throw new IncompatibleUnitException();
                }
                registerConverter(converter);
            } else {
                converter = reverseConverter.reverse();
            }
    
        }
        
        return converter.fowardConvert(original);
    }
    
    record ScaleFactor(Unit<?> unit, Rational factor){}
    
    private ScaleFactor resolveScaleFactor(Unit<?> unit){
        return switch (unit){
            case MultipleUnit(String s, Rational factor,  Unit<?> ref) -> new ScaleFactor(ref, factor);
            case BaseUnit<?> baseUnit -> new ScaleFactor(baseUnit, Rational.ONE);
            case ComposedUnit<?> composedUnit -> new ScaleFactor(composedUnit, Rational.ONE);
        };
    }
}

