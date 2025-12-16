package org.midheaven.quantity;

import org.midheaven.quantity.measurables.Measurable;

class UnitOperations {
    
    static <R extends Measurable, M extends Measurable, T extends Measurable> Unit<R> times(Unit<M> a, Unit<T> b) {
        return new ComposedUnit<>(compositionOf(a).times(compositionOf(b)) , a.dimension().times(b.dimension()));
    }
    
    static <R extends Measurable, M extends Measurable, T extends Measurable> Unit<R> over(Unit<M> a, Unit<T> b) {
        return new ComposedUnit<>(compositionOf(a).over(compositionOf(b)) , a.dimension().over(b.dimension()));
    }
    
    private static <T extends Measurable> Composition compositionOf(Unit<T> unit){
        return switch (unit){
            case BaseUnit<T> base -> new Composition(base);
            case ComposedUnit<T>(Composition c, Dimension<T> d) -> c;
            case MultipleUnit<T>  multiple -> new Composition(multiple);
        };
    }
    
    public static <R extends Measurable, M extends Measurable> Unit<R> raise(Unit<M> unit, int exponent) {
        return new ComposedUnit<>(compositionOf(unit).raise(exponent), unit.dimension().raise(exponent));
    }
    
    private UnitOperations(){}
}
