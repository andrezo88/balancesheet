package com.andre.balancesheet.configs;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.parse;

@Configuration
public class LocalDateTimeTypeAdapter extends TypeAdapter<LocalDateTime> implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        return parse(jsonElement.getAsString(), formatter);
    }

    @Override
    public JsonElement serialize(LocalDateTime localDate, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(localDate.format(formatter));
    }

    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime value) throws IOException {
        if(value == null) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(formatter.format(value));
        }
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        if (in != null) {
            return parse(in.nextString(), formatter);
        } else {
            return null;
        }
    }
}
