package ru.acme.web.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Arrays;
import java.util.List;

public class JsonObject {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> ObjectNode getJsonIgnoreFields(T jsonObject, String... field) {
        objectMapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
            @Override
            public boolean hasIgnoreMarker(final AnnotatedMember m) {
                List<String> exclusions = Arrays.asList(field);
                return exclusions.contains(m.getName()) || super.hasIgnoreMarker(m);
            }
        });
        return objectMapper.valueToTree(jsonObject);
    }

    public static <T> T convertToObject(JsonNode node, Class<T> clazz) throws JsonProcessingException {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper.treeToValue(node, clazz);
    }

    public static <T> T readValue(String s, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(s, clazz);
    }

    public static <T> String writeValue(T obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
