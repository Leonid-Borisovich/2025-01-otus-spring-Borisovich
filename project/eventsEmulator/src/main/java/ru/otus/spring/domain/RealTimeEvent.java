package ru.otus.spring.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

/**
 {
 "eventId": "id_2",
 "timestamp": "2025-07-27 15:04",
 "eventDescription": "Пересечение периметра 2: 2025-07-27 15:04",
 "channelId": "ID_Южные_ворота",
 "channelName": "Южные ворота",
 "comment": "Пересечение периметра на Южных воротах"
 }
 */


@NoArgsConstructor
@AllArgsConstructor
@Data
public class RealTimeEvent {

    @NotBlank
    private String eventId;
    private String timestamp;
    @NotBlank
    private String eventDescription;
    @NotBlank
    private String channelId;
    private String channelName;
    private String comment;

    public RealTimeEvent(String eventId, String comment) {
        this.eventId = eventId;
        this.comment = comment;
    }

 }
