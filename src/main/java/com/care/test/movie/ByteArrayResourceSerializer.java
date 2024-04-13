package com.care.test.movie;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;
import java.io.InputStream;

public class ByteArrayResourceSerializer extends JsonSerializer<ByteArrayResource> {

    @Override
    public void serialize(ByteArrayResource value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        InputStream inputStream = value.getInputStream();
        try {
            byte[] bytes = inputStream.readAllBytes();
            gen.writeBinary(bytes);
        } finally {
            inputStream.close();
        }
    }

    @Override
    public Class<ByteArrayResource> handledType() {
        return ByteArrayResource.class;
    }
}
