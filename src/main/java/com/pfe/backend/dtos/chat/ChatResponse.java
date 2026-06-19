package com.pfe.backend.dtos.chat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ChatResponse {
    private String message;
    private List<?> events;
    private SearchCriteria criteresUtilises;
    private boolean hasResults;
    private long total;
}



