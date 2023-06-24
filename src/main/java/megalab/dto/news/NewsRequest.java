package megalab.dto.news;

import lombok.Builder;

import java.util.List;

@Builder
public record NewsRequest(String name,
                          String image,
                          String description,
                          String text,
                          List<Long> categoryIds) {
    public NewsRequest {
    }
}
