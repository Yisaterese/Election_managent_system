package africa.semicolon.election_management_system.data.models;

import africa.semicolon.election_management_system.data.constants.Category;
import africa.semicolon.election_management_system.data.constants.Role;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.LocalDateTime.now;
import static lombok.AccessLevel.NONE;

@Entity
@Table(name = "candidates")
@Getter
@Setter
public class Candidate {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String identificationNumber;
    private String password;
    private String address;
    private LocalDate dateOfBirth;
    private String stateOfOrigin;
    private String partyAffiliation;
    @Enumerated(STRING)
    private Category positionContested;

    private Long votingId;
    @Enumerated(value = STRING)
    private Role role;
    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;

    @Setter(NONE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateRegistered;

    @Setter(NONE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateUpdated;

    @PrePersist
    private void setDateRegistered() {
        dateRegistered = now();
    }

    @PreUpdate
    private void setDateUpdated() {
        dateUpdated = now();
    }

}
