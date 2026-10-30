package org.midheaven.quantity;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Composition {
    
 
    interface Composable {
        boolean isIdentity();
        String symbol();
    }
    
    record Term(Composable value, int exponent){}
    
    private final Map<Composable, Integer> terms;
    
    Composition(Composable term){
        terms = new HashMap<>();
        terms.put(term, 1);
    }
    
    private Composition(){
        terms = new HashMap<>();
    }
    
    private Composition(Composition other){
        terms = new HashMap<>(other.terms);
    }
    
    int termsCount (){
        return terms.size();
    }
    
    Composition times(Composition other){
        return combine(other, +1);
    }
    
    Composition over(Composition other){
        return combine(other, -1);
    }
    
    private Composition combine(Composition other, int sign){
        var current = new HashMap<>(this.terms);
        var result = new Composition();
        for (var entry : other.terms.entrySet()){
            var existing = current.remove(entry.getKey());
            if (existing == null){
                existing = 0;
            }
            var v = existing + sign * entry.getValue();
            if (v != 0){
                result.terms.put(entry.getKey(), v);
            }
        }
        
        for (var entry : current.entrySet()){
            var v = entry.getValue();
            if (v != 0){
                result.terms.put(entry.getKey(), v);
            }
        }
        
        var iterator = result.terms.keySet().iterator();
        while (iterator.hasNext()){
            if (iterator.next().isIdentity()){
                iterator.remove();
                break;
            }
        }
        return result;
    }
    
    Composition raise(int exponent) {
        var result = new Composition();
        for (var entry : this.terms.entrySet()){
            result.terms.put(entry.getKey(),  entry.getValue() * exponent);
        }
        return result;
    }
    
    Stream<Term> terms(){
        return terms.entrySet().stream().map(e -> new Term(e.getKey(), e.getValue()));
    }
    
    @Override
    public boolean equals(Object other){
        if (other instanceof Composition that){
            return this.terms.equals(that.terms);
        }
        return false;
    }
    
    
    @Override
    public int hashCode(){
        return terms.size();
    }
    
    @Override
    public String toString(){
        return terms.toString();
    }
    
    public String symbol() {
        return terms.entrySet().stream()
         .filter(entry -> entry.getValue() != 0)
        .map(entry -> {
            if (entry.getValue() == 1){
                return entry.getKey().symbol();
            } else {
                return entry.getKey().symbol() + "^" + entry.getValue();
            }
        }).collect(Collectors.joining(" "));
    }
    
}
