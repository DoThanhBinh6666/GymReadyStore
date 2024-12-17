package com.project.shopapp.services.announcement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shopapp.responses.announcement.AnnouncementResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementRedisService implements IAnnouncementRedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Value("${spring.data.redis.use-redis-cache}")
    private boolean useRedisCache;

    public AnnouncementRedisService(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    private String getKeyFrom(String keyword, PageRequest pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        return String.format("all_announcements:%s:%d:%d", keyword, pageNumber, pageSize);
    }

    @Override
    public List<AnnouncementResponse> getAllAnnouncements(String keyword, PageRequest pageRequest) throws JsonProcessingException {
        if (!useRedisCache) {
            return null;
        }

        String key = getKeyFrom(keyword, pageRequest);
        String json = (String) redisTemplate.opsForValue().get(key);

        return json != null ? objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, AnnouncementResponse.class)) : null;
    }

    @Override
    public void clear() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    @Override
    public void saveAllAnnouncements(List<AnnouncementResponse> announcementResponses, String keyword, PageRequest pageRequest) throws JsonProcessingException {
        String key = getKeyFrom(keyword, pageRequest);
        String json = objectMapper.writeValueAsString(announcementResponses);
        redisTemplate.opsForValue().set(key, json);
    }
}
