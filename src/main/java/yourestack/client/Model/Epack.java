package yourestack.client.Model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Epack {

    private Long epackId;

    private String epackName;

    private String description;

    private int manager_id;

    private double price;

    private Long categoryId;

    @CreatedDate
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    private OffsetDateTime lastUpdated;

    private boolean isActive;

    private Long order_id;
}