package africa.semicolon.election_management_system.data.models;

import africa.semicolon.election_management_system.data.constants.Category;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "elections")
@Getter
@Setter
public class Election {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String title;
    @Enumerated(STRING)
    private Category category;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDate;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Election election) && Objects.equals(this.id, election.id);
    }

}
