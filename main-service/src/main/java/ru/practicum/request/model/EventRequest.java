package ru.practicum.request.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode(of = { "id" })
public class EventRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "requester_id")
    private Long requesterId;

    @Column(name = "status")
    private EventRequestStatus status;

    @Column(name = "created")
    private LocalDateTime created;
}
