package com.hospitalhiberus;

import com.hospitalhiberus.model.ESTADOS;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EstadoConverter implements AttributeConverter<ESTADOS, String> {

    @Override
    public String convertToDatabaseColumn(ESTADOS attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public ESTADOS convertToEntityAttribute(String dbData) {
        return dbData == null ? null : ESTADOS.valueOf(dbData);
    }
}
