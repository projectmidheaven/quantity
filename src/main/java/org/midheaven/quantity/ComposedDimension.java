package org.midheaven.quantity;

import org.midheaven.lang.ValueClass;
import org.midheaven.quantity.measurables.Measurable;

@ValueClass
record ComposedDimension<M extends Measurable>(Composition composition) implements Dimension<M>  {
    
    @Override
    public <R extends Measurable, T extends Measurable> Dimension<R> times(Dimension<T> other) {
        return switch (other){
            case BaseDimension<T> base -> new ComposedDimension<>(composition.times(new Composition(base)));
            case ComposedDimension<T>(Composition inner) -> new ComposedDimension<>(composition.times(inner));
        };
    }
    
    @Override
    public <R extends Measurable, T extends Measurable> Dimension<R> over(Dimension<T> other) {
        return switch (other){
            case BaseDimension<T> base -> new ComposedDimension<>(composition.over(new Composition(base)));
            case ComposedDimension<T>(Composition inner) -> new ComposedDimension<>(composition.over(inner));
        };
    }
    
    @Override
    public <R extends Measurable> Dimension<R> raise(int exponent) {
        return new ComposedDimension<>(composition.raise(exponent));
    }
}
