package hr.algebra.util.converter;

import com.fasterxml.jackson.databind.util.StdConverter;

public class IntegerToStringConverter extends StdConverter<Integer, String> {

    @Override
    public String convert(Integer integer) {
        return integer != null ? String.valueOf(integer) : null;
    }
}
