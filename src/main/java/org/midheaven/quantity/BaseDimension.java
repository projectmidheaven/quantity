package org.midheaven.quantity;

import org.midheaven.lang.ValueClass;
import org.midheaven.quantity.measurables.Measurable;

@ValueClass
record BaseDimension<M extends Measurable>(String axis) implements Dimension<M> , Composition.Composable {
    
    
    public static <Q extends Measurable> BaseDimension<Q> ofAxis(String axis) {
        return new BaseDimension<>(axis);
    }
    
    BaseDimension(String axis) {
        this.axis = axis;
    }
    
    @Override
    public <R extends Measurable, T extends Measurable> Dimension<R> times(Dimension<T> other) {
        return switch (other){
            case BaseDimension<T> base -> new ComposedDimension<>(new Composition(this).times(new Composition(base)));
            case ComposedDimension<T>(Composition inner) -> new ComposedDimension<>(new Composition(this).times(inner));
        };
    }
    
    @Override
    public <R extends Measurable, T extends Measurable> Dimension<R> over(Dimension<T> other) {
        return switch (other){
            case BaseDimension<T> base -> new ComposedDimension<>(new Composition(this).over(new Composition(base)));
            case ComposedDimension<T>(Composition inner) -> new ComposedDimension<>(new Composition(this).over(inner));
        };
    }
    
    @Override
    public <R extends Measurable> Dimension<R> raise(int exponent) {
        return new ComposedDimension<>(new Composition(this).raise(exponent));
    }
    
    @Override
    public boolean equals(Object other){
        return other instanceof BaseDimension(String otherAxis)
            && this.axis.equals(otherAxis);
    }
    
    @Override
    public boolean isIdentity() {
        return this.axis.isEmpty();
    }
}
