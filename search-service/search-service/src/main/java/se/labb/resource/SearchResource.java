package se.labb.resource;

import se.labb.model.Patient;
import se.labb.model.Notation;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Path("/api/search")
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {

    @GET
    @Path("/patients")
    public Uni<List<Patient>> searchPatients(
            @QueryParam("name") String name,
            @QueryParam("condition") String diagnosis) {

        if (diagnosis != null && !diagnosis.isBlank()) {
            // 1. Hitta alla notationer med denna diagnos
            return Notation.find("diagnosis like ?1", "%" + diagnosis + "%")
                    .list()
                    .chain(notations -> {
                        List<Long> patientUserIds = notations.stream()
                                .map(n -> ((Notation) n).receiverUserId)
                                .distinct()
                                .toList();

                        if (patientUserIds.isEmpty()) {
                            return Uni.createFrom().item(List.of());
                        }

                        return Patient.list("userId in ?1", patientUserIds);
                    });
        }

        if (name != null && !name.isBlank()) {
            return Patient.list("firstName like ?1 or lastName like ?1", "%" + name + "%");
        }

        return Uni.createFrom().item(List.of());
    }

    @GET
    @Path("/practitioner/{id}/encounters")
    public Uni<List<Notation>> getDailySchedule(
            @PathParam("id") Long doctorUserId,
            @QueryParam("date") String dateStr) {

        if (dateStr == null) return Uni.createFrom().item(List.of());

        LocalDate date = LocalDate.parse(dateStr);
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        return Notation.list("creatorUserId = ?1 and date >= ?2 and date < ?3 ORDER BY date ASC",
                doctorUserId, start, end);
    }
}