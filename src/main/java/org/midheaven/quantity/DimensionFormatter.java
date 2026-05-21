package org.midheaven.quantity;

import org.midheaven.collections.Sequence;
import org.midheaven.lang.Strings;
import org.midheaven.quantity.measurables.Measurable;

public class DimensionFormatter {
    
    public String format(Dimension<?> dimension){
        return switch (dimension){
            case BaseDimension<?> base -> base.axis();
            case ComposedDimension<?> composed -> {
                StringBuilder top = new StringBuilder();
                StringBuilder bottom = new StringBuilder();
                composed.composition().terms().forEach(term -> {
                    if (term.exponent() == 1){
                        top.append(((BaseDimension<?>)term.value()).axis()).append(" ");
                    } else if (term.exponent() == -1){
                        bottom.append(((BaseDimension<?>)term.value()).axis()).append(" ");
                    } else if (term.exponent() > 0){
                        top.append(((BaseDimension<?>)term.value()).axis()).append("^").append(term.exponent()).append(" ");
                    } else if (term.exponent() < 0){
                        bottom.append(((BaseDimension<?>)term.value()).axis()).append("^").append(-term.exponent()).append(" ");
                    }
                });
                if (!bottom.isEmpty()){
                    top.append("/ ").append(bottom);
                }
                yield top.toString().trim();
            }
        };
    }
    
    
    
    public <M extends Measurable> Dimension<M> parse(String text){
        if (text == null){
            return null;
        } else if (text.isEmpty()){
            return (Dimension<M>) StandardDimensions.DIMENSIONLESS;
        }
        
        // M^2 / L^4 T^3
        if (text.contains("/")){
            var split = Strings.Splitter.split(text).by("/");
            var multiply = Strings.Splitter.split(split.get(0).trim()).by(" ");
            var divide = Strings.Splitter.split(split.get(1).trim()).by(" ");
            
            return parseSequence(multiply.sequence()).over(parseSequence(divide.sequence()));
          
        }
        
        var split = Strings.Splitter.split(text).by(" ");
        if (split.count().toInt() == 1){
            return BaseDimension.ofAxis(text);
        }
        
        return (Dimension<M>) parseSequence(split.sequence());
    }
    
    private Dimension<?> parseSequence(Sequence<String> sequence){
        return sequence.map(it -> {
            var s = Strings.Splitter.split(it).by("^");
            return s.count().toInt() == 2 ? BaseDimension.ofAxis(s.get(0)).raise(Integer.parseInt(s.get(1))) : BaseDimension.ofAxis(s.get(0));
        }).reduce(Dimension::times).orNull();
    }
}
