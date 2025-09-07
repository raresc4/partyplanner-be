package org.example.backend.models;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Task {
    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private int progress;

    @NotNull
    private String assignee;
}
