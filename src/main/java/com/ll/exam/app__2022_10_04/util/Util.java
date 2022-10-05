package com.ll.exam.app__2022_10_04.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.exam.app__2022_10_04.AppConfig;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;
public class Util {
    public static class json{
        private static ObjectMapper getObjectMapper(){
            return (ObjectMapper) AppConfig.getContext().getBean("objectMapper");
        }
        public static Object toStr(Map<String, Object> map) {
            try {
                return getObjectMapper().writeValueAsString(map);
            } catch (JsonProcessingException e) {
                return null;
            }
        }
        public static Map<String,Object> toMap(String jsonStr) {
            try {
                return getObjectMapper().readValue(jsonStr, LinkedHashMap.class);
            } catch (JsonProcessingException e) {
                return null;
            }
        }
    }
}
