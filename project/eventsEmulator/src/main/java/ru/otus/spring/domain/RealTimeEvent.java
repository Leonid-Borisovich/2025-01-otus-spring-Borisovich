package ru.otus.spring.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 {
 "EventId" : "eb0bb455-b85f-4ac4-851f-f30a11797579",
 "Timestamp" : "19.10.2022 09:58:55",
 "BinaryTimestamp" : "5249703721781162729",
 "ZonedTimestamp" : "19.10.2022 09:58:55.377 +05:00",
 "EventDescription" : "Начало движения",
 "IsAlarmEvent" : "False",
 "ChannelId" : "e0391a80-c921-4ffc-9a69-107fcf28e34e",
 "ChannelName" : "Камера 3",
 "Comment" : "",
 "EventType" : "Info",
 "InitiatorName" : "System"
 }
 */


@NoArgsConstructor
@AllArgsConstructor
@Data
public class RealTimeEvent {

    private String eventId;
    private String timestamp;
    private String eventDescription;
    private String channelId;
    private String channelName;
    private String comment;

    public RealTimeEvent(String eventId, String comment) {
        this.eventId = eventId;
        this.comment = comment;
    }

 }
