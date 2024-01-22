package com.example.dossier.config;

import com.example.dossier.dto.Application;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import com.example.dossier.dto.EmailDto;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CustomDeserializer implements Deserializer<Object> {

    private final String encoding = StandardCharsets.UTF_8.name();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public Object deserialize(String s, byte[] data) {
        try {
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
