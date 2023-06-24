package megalab.dto.category;

import lombok.Builder;

@Builder
public record CategoryRequest(String name) {
    public CategoryRequest {
    }
}
