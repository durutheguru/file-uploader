package com.julianduru.fileuploader.util;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.time.ZoneId.systemDefault;
import static java.time.ZonedDateTime.ofInstant;


@Converter
public class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, Timestamp> {


    @Override
    public Timestamp convertToDatabaseColumn(ZonedDateTime attribute) {
        return attribute == null ? null : Timestamp.valueOf(
            attribute.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
        );
    }


    @Override
    public ZonedDateTime convertToEntityAttribute(Timestamp dbData) {
        return dbData == null ? null : ofInstant(dbData.toInstant(), systemDefault());
    }


}