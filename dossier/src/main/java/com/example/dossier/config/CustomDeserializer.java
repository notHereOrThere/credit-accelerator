package com.example.dossier.config;

import com.example.dossier.dto.Application;
import com.example.dossier.dto.EmailDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class CustomDeserializer implements Deserializer<Object> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public Object deserialize(String s, byte[] data) {
        try {
            mapper.registerModule(new JavaTimeModule());
            JsonNode jsonNode = mapper.readTree(data);
            try {
                return mapper.treeToValue(jsonNode, EmailDto.class);
            } catch (Exception e) {
                try {
                    return mapper.treeToValue(jsonNode, Application.class);
                } catch (Exception e1) {
                    throw  new SerializationException("Error when deserializing byte[] to StringValue", e1);
                }
            }
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to StringValue", e);
        }
    }

    @Override
    public Object deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
