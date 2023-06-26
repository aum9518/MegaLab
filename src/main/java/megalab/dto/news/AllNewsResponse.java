package megalab.dto.news;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor

public class AllNewsResponse {
    private Long id;
    private String name;
    private String image;
    private String description;
    private String createdAt;
    @Builder
    public AllNewsResponse(Long id, String name, String image, String description, java.sql.Date createdAt) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = Instant.ofEpochMilli(createdAt.getTime());

        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.createdAt = dateTimeFormatter.format(zonedDateTime);
    }

}
