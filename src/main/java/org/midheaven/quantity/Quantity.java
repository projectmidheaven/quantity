package org.midheaven.quantity;

import org.midheaven.lang.Check;
import org.midheaven.lang.NotNullable;
import org.midheaven.lang.Ordered;
import org.midheaven.lang.Signed;
import org.midheaven.math.AdditionGroup;
import org.midheaven.math.Arithmetic;
import org.midheaven.math.Interval;
import org.midheaven.math.MultiplicationGroup;
import org.midheaven.math.Rational;
import org.midheaven.quantity.measurables.Measurable;

public interface Quantity<M extends Measurable> extends Signed<Quantity<M>>, Ordered<Quantity<M>>, AdditionGroup<Quantity<M>>, MultiplicationGroup<Quantity<M>> {
    
    static @NotNullable <M extends Measurable>  Arithmetic<Quantity<M>, Quantity<M>> arithmetic(@NotNullable Unit<M> unit){
        Check.argumentIsNotNull(unit, "unit");
        return new QuantityArithmetic<M>(unit);
    }
    
    static @NotNullable <M extends Measurable>  Arithmetic<Quantity<M>, Quantity<M>> arithmetic(Unit<M> unit, QuantityConversion conversion){
        return new ConvertingQuantityArithmetic<M>(unit, conversion);
    }
    
    static <M extends Measurable> Interval.Domain<Quantity<M>, Quantity<M>> domain(){
        return new Interval.Domain<>() {
            @Override
            public int compare(Quantity<M> a, Quantity<M> b) {
                return a.compareTo(b);
            }
            
            @Override
            public Quantity<M> applyMinimum(Quantity<M> value) {
                return value;
            }
            
            @Override
            public Quantity<M> applyMaximum(Quantity<M> value) {
                return value;
            }
        };
    }
    
    static <M extends Measurable> Interval.Domain<Quantity<M>, Quantity<M>> domain(@NotNullable QuantityConversion conversion){
        return new Interval.Domain<>() {
            @Override
            public int compare(Quantity<M> a, Quantity<M> b) {
                return a.compareTo(b, conversion);
            }
            
            @Override
            public Quantity<M> applyMinimum(Quantity<M> value) {
                return value;
            }
            
            @Override
            public Quantity<M> applyMaximum(Quantity<M> value) {
                return value;
            }
        };
    }
    
    int compareTo(Quantity<M> other, QuantityConversion conversion);
    
    @NotNullable Rational magnitude();
    @NotNullable Unit<M> unit();
    
    @NotNullable Quantity<M> minus(@NotNullable Quantity<M> other, @NotNullable  QuantityConversion conversion);
    @NotNullable Quantity<M> plus(@NotNullable Quantity<M> other, @NotNullable QuantityConversion conversion);
    
    @Override
    int compareTo(Quantity<M> o);
    
    @Override
    Quantity<M> plus(Quantity<M> other);
    
    @Override
    int sign();
    
    @Override
    Quantity<M> negate();
    
    @Override
    boolean isZero();
    
    @Override
    Quantity<M> minus(Quantity<M> other);
    
    @NotNullable Quantity<M> abs();
    
    @NotNullable  Quantity<M> times(@NotNullable Rational factor);
    @NotNullable Quantity<M> over(@NotNullable Rational factor);
    
    default @NotNullable Quantity<M> times(long factor){
        return times(Rational.of(factor));
    }
    
    default @NotNullable Quantity<M> over(long factor){
        return over(Rational.of(factor));
    }
    

}
