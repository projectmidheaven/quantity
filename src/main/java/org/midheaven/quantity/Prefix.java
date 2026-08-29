package org.midheaven.quantity;

import org.midheaven.math.Rational;
import org.midheaven.quantity.measurables.Measurable;

import java.util.Map;
import java.util.Optional;

public class Prefix {
    
    
    // see https://en.wikipedia.org/wiki/Metric_prefix
    private final static Map<String, Rational> factors = Map.ofEntries(
        Map.entry("Y", Rational.TEN.raisedTo(24)),
        Map.entry("Z", Rational.TEN.raisedTo(21)),
        Map.entry("E", Rational.TEN.raisedTo(18)),
        Map.entry("P", Rational.TEN.raisedTo(15)),
        Map.entry("T", Rational.TEN.raisedTo(12)),
        Map.entry("G", Rational.TEN.raisedTo(9)),
        Map.entry("M", Rational.TEN.raisedTo(6)),
        Map.entry("k", Rational.TEN.raisedTo(3)),
        Map.entry("h", Rational.TEN.raisedTo(2)),
        Map.entry("da", Rational.TEN),
        Map.entry("d", Rational.TEN.invert()),
        Map.entry("c", Rational.TEN.raisedTo(2).invert()),
        Map.entry("m", Rational.TEN.raisedTo(3).invert()),
        Map.entry("μ", Rational.TEN.raisedTo(6).invert()),
        Map.entry("u", Rational.TEN.raisedTo(6).invert()),
        Map.entry("n", Rational.TEN.raisedTo(9).invert()),
        Map.entry("p", Rational.TEN.raisedTo(12).invert()),
        Map.entry("f", Rational.TEN.raisedTo(15).invert()),
        Map.entry("a", Rational.TEN.raisedTo(18).invert()),
        Map.entry("z", Rational.TEN.raisedTo(21).invert()),
        Map.entry("y", Rational.TEN.raisedTo(24).invert())
    );
    
    private final static Map<String, Integer> powers = Map.ofEntries(
        Map.entry("Y", 24),
        Map.entry("Z",21),
        Map.entry("E", 18),
        Map.entry("P", 15),
        Map.entry("T", 12),
        Map.entry("G",9),
        Map.entry("M", 6),
        Map.entry("k", 3),
        Map.entry("h", 2),
        Map.entry("da",1),
        Map.entry("d", -1),
        Map.entry("c", -2),
        Map.entry("m", -3),
        Map.entry("μ", -6),
        Map.entry("u", -6),
        Map.entry("n", -9),
        Map.entry("p", -12),
        Map.entry("f", -15),
        Map.entry("a", -18),
        Map.entry("z", -21),
        Map.entry("y", -24)
    );
    
    static Optional<Rational> factorOf(String prefix) {
        if (prefix.equals("u")){
            prefix = "μ";
        }
        return Optional.ofNullable(factors.get(prefix));
    }
    
    static Optional<Integer> powerOf(String prefix) {
        if (prefix.equals("u")){
            prefix = "μ";
        }
        return Optional.ofNullable(powers.get(prefix));
    }
    
    public static <M extends Measurable> Unit<M> prefixWithPower(Unit<M> original, int power) {
        return powers.entrySet().stream().filter(entry -> entry.getValue() == power).findFirst()
            .map(entry -> of(entry.getKey(), original)).orElse(original);
    }
    // Multiples
    
    static <M extends Measurable> Unit<M> of(String prefix, Unit<M> base){
        return new MultipleUnit<>(prefix + base.symbol(), factors.get(prefix), base);
    }
    
    public static <M extends Measurable> Unit<M> yotta(Unit<M> base){
        return of("Y", base);
    }
    
    public static <M extends Measurable> Unit<M> zeta(Unit<M> base){
        return of("Z", base);
    }
    
    public static <M extends Measurable> Unit<M> exa(Unit<M> base){
        return of("E", base);
    }
    
    public static <M extends Measurable> Unit<M> peta(Unit<M> base){
        return of("P", base);
    }
    
    public static <M extends Measurable> Unit<M> tera(Unit<M> base){
        return of("T", base);
    }
    
    public static <M extends Measurable> Unit<M> giga(Unit<M> base){
        return of("G", base);
    }
    
    public static <M extends Measurable> Unit<M> mega(Unit<M> base){
        return of("M", base);
    }
    
    public static <M extends Measurable> Unit<M> kilo(Unit<M> base){
        return of("k", base);
    }
    
    public static <M extends Measurable> Unit<M> hecto(Unit<M> base){
        return of("h", base);
    }
    
    public static <M extends Measurable> Unit<M> deca(Unit<M> base){
        return of("da", base);
    }
    
    // Submultiples
    
    public static <M extends Measurable> Unit<M> deci(Unit<M> base){
        return of("d", base);
    }
    
    public static <M extends Measurable> Unit<M> centi(Unit<M> base){
        return of("c", base);
    }
    
    public static <M extends Measurable> Unit<M> milli(Unit<M> base){
        return of("m", base);
    }
    
    public static <M extends Measurable> Unit<M> micro(Unit<M> base){
        return of("μ", base);
    }
    
    public static <M extends Measurable> Unit<M> nano(Unit<M> base){
        return of("n", base);
    }
    
    public static <M extends Measurable> Unit<M> pico(Unit<M> base){
        return of("p", base);
    }
    
    public static <M extends Measurable> Unit<M> femto(Unit<M> base){
        return of("f", base);
    }
    
    public static <M extends Measurable> Unit<M> ato(Unit<M> base){
        return of("a", base);
    }
    
    public static <M extends Measurable> Unit<M> zepto(Unit<M> base){
        return of("z", base);
    }
    
    public static <M extends Measurable> Unit<M> yocto(Unit<M> base){
        return of("y", base);
    }
    
}
