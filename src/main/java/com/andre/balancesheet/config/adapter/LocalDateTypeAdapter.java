package com.andre.balancesheet.config.adapter;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

@Configuration
public class LocalDateTypeAdapter extends TypeAdapter<LocalDate> implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    private static final DateTimeFormatter formatter = ISO_LOCAL_DATE;

    @Override
    public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        return parse(jsonElement.getAsString(), formatter);
    }

    @Override
    public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(localDate.format(formatter));
    }

    @Override
    public void write(JsonWriter jsonWriter, LocalDate value) throws IOException {
        if(value == null) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(formatter.format(value));
        }
    }

    @Override
    public LocalDate read(JsonReader in) throws IOException {
        if (in != null) {
            return LocalDate.parse(in.nextString(), formatter);
        } else {
            return null;
        }
    }
}
