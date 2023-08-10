package com.vv.task.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MapperUtils {
    private final ObjectMapper objectMapper;

    public <T> T getDTOFromJson(JsonNode jsonNode, Class<T> type) {
        T t = null;
        try {
            t = objectMapper.treeToValue(jsonNode, type);
        } catch (JsonProcessingException e) {
            log.error(e.toString());
        }
        return t;
    }
}
