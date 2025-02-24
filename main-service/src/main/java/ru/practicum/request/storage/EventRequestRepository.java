package ru.practicum.request.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.request.model.EventRequest;

import java.util.Collection;

public interface EventRequestRepository extends JpaRepository<EventRequest, Long> {

    @Query(value = """
            SELECT *
            FROM requests AS r
            WHERE requester_id = ?1
                AND event_id = ?2
        """,
        nativeQuery = true
    )
    EventRequest findByRequesterAndEvent(Long requesterId, Long eventId);

    Collection<EventRequest> findByRequesterId(Long requesterId);

    Collection<EventRequest> findByEventId(Long eventId);
}
