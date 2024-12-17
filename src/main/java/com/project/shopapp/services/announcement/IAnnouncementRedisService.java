package com.project.shopapp.services.announcement;

import com.project.shopapp.responses.announcement.AnnouncementResponse;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IAnnouncementRedisService {
    void clear();
    List<AnnouncementResponse> getAllAnnouncements(String keyword, PageRequest pageRequest) throws Exception;
    void saveAllAnnouncements(List<AnnouncementResponse> announcementResponses, String keyword, PageRequest pageRequest) throws Exception;
}
