package org.example.backend.dto;

import com.sun.istack.NotNull;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import org.example.backend.models.Task;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class EventDto {
    private String id;

    @NotNull
    private String title;

    @NotNull
    private String location;

    @NotNull
    private Date date;

    @NotNull
    @Valid
    private List<String> involvedUsers;

    @NotNull
    @Valid
    private List<Task> tasks;

    @NotNull
    private String admin;
}
