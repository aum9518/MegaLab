package megalab.dto.news;

import lombok.Builder;

@Builder
public record NewsRequest(String name,
                          String image,
                          String description,
                          String text) {
    public NewsRequest {
    }
}
