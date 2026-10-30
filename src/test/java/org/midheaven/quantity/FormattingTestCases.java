package org.midheaven.quantity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormattingTestCases {
    
    @Test
    public void dimensionFormatIsConsistent() {
        record Case(String format, Dimension<?> dim){}
        
        var list = List.of(
            new Case(null, null),
            new Case("", StandardDimensions.DIMENSIONLESS),
            new Case("L", StandardDimensions.DISTANCE),
            new Case("L / T", StandardDimensions.VELOCITY),
            new Case("L M / T^2", StandardDimensions.FORCE),
            new Case("L^2 M / T^2", StandardDimensions.ENERGY),
            new Case("L^2", StandardDimensions.AREA),
            new Case("T L^2", StandardDimensions.AREA.times(StandardDimensions.TIME))
        );
        
        var formatter = new DimensionFormatter();
        
        for (var item : list) {
            var formated = formatter.format(item.dim);
            assertEquals(item.format, formated);
            assertEquals(item.dim, formatter.parse(formated));
        }
        
    }
    
}
