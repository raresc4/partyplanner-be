package org.example.backend.models;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class Event {
    @Id
    private String id;
    private String title;
    private String location;
    private Date date;
    private List<String> involvedUsers;
    private List<Task> tasks;
    private String admin;
}
