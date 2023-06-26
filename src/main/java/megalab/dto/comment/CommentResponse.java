package megalab.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class CommentResponse {
    private Long id;
    private String text;
    private String updatedAt;
    private List<CommentResponse> comments;
    @Builder
    public CommentResponse(Long id, String text,  java.sql.Date updated) {
        this.id = id;
        this.text = text;

        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = Instant.ofEpochMilli(updated.getTime());
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.updatedAt = dateTimeFormatter.format(zonedDateTime);
    }
    @Builder
    public CommentResponse (Long id, String text, ZonedDateTime zonedDateTime){
        this.id = id;
        this.text = text;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.updatedAt = dateTimeFormatter.format(zonedDateTime);
    }
}
