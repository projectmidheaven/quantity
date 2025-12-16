package org.midheaven.quantity;

import org.midheaven.quantity.measurables.Measurable;

public sealed interface Dimension<M extends Measurable>
    permits BaseDimension, ComposedDimension {
    
    <R extends Measurable, T extends Measurable> Dimension<R> times(Dimension<T> other);
    <R extends Measurable, T extends Measurable> Dimension<R> over(Dimension<T> other);
    <R extends Measurable> Dimension<R> raise(int exponent);
}
