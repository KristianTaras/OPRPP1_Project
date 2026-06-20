package hr.algebra.util.converter;

import com.fasterxml.jackson.databind.util.StdConverter;

public class DoubleToStringConverter extends StdConverter<Double, String> {
    @Override
    public String convert(Double aDouble) {
        return aDouble != null ? String.valueOf(aDouble) : null;
    }
}
