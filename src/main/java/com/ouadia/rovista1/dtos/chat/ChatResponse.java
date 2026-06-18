package com.ouadia.rovista1.dtos.chat;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ChatResponse {
    private String message;
    private List<?> events;
    private SearchCriteria criteresUtilises;
    private long total;
}



