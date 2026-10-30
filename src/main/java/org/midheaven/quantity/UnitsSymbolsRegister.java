package org.midheaven.quantity;

import org.midheaven.collections.Sequence;
import org.midheaven.lang.Maybe;
import org.midheaven.lang.Strings;
import org.midheaven.quantity.measurables.Measurable;

import java.util.HashMap;
import java.util.Map;

public class UnitsSymbolsRegister {
    
    private final static UnitsSymbolsRegister ME = new UnitsSymbolsRegister();
    
    private final Map<Unit, String> symbols = new HashMap<>();
    
    public static UnitsSymbolsRegister instance(){
        return ME;
    }
    
    static <E extends Measurable>  Unit<E> register(Unit<E> unit,String symbol){
        instance().registerUnitSymbol(unit, symbol);
        return unit;
    }
    
    public <E extends Measurable>  UnitsSymbolsRegister registerUnitSymbol(Unit<E> unit,String symbol){
        symbols.put(unit, symbol);
        return this;
    }
    
    public Sequence<Unit<?>> all(){
        return Sequence.builder().from(symbols.keySet()).map(it -> (Unit<?>)it);
    }
    
    public <M extends Measurable> Maybe<String> symbolOf(Unit<M> unit) {
        return Maybe.of(this.symbols.get(unit));
    }
    
    public <E extends Measurable> Maybe<Unit<E>> unitOf(String text) {
        if (text == null){
            return Maybe.none();
        }
        text = text.trim();
        
        for (var entry : symbols.entrySet()){
            if (entry.getValue().equals(text)){
                return   Maybe.of(entry.getKey());
            }
        }
        
        if (text.length() > 1){
            var pos = text.indexOf("/");
            if (pos > 0){
                var n = unitOf(text.substring(0, pos).trim());
                var d = unitOf(text.substring(pos + 1).trim());
                return n.zip(d, Unit::over);
            }
            Unit<E> result = null;
         
            for (var unitText : Strings.Splitter.split(text).by(" ").sequence()){
                var u = parsePower(unitText);
                if (u.isPresent()){
                    if (result == null){
                        result = (Unit<E>) u.get();
                    } else {
                        result = result.times((Unit<E>) u.get());
                    }
                }
            }
            
            return Maybe.of(result);
        }
        return Maybe.none();

    }
    
    private <E extends Measurable> Maybe<Unit<E>> parsePower(String unitText) {
        if (unitText.contains("^")){
            var inner = Strings.Splitter.split(unitText).by("\\^");
            var power = Integer.parseInt(inner.get(1));
            return unitOf(inner.get(0)).map(it -> it.raise(power));
        } else if (unitText.length() > 1){
                var prefix = unitText.startsWith("da") ? "da" : unitText.substring(0, 1);
                if (Prefix.factorOf(prefix).isPresent()) {
                    return unitOf(unitText.substring(prefix.length())).map(it -> (Unit<E>) Prefix.of(prefix, it));
                }
        }
        return unitOf(unitText);
    }
}
