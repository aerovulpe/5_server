package io.wolfbeacon.server.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Aaron on 10/06/2016.
 */
@Entity
@Table(name = "attending_status")
@AssociationOverrides({
        @AssociationOverride(name = "id.user",
                joinColumns = @JoinColumn(name = "userId")),
        @AssociationOverride(name = "id.hackathonEvent",
                joinColumns = @JoinColumn(name = "eventId"))})
public class AttendingStatus implements Serializable {
    private enum Status {
        ATTENDING, DECLINED
    }

    @EmbeddedId
    private AttendingStatusId id = new AttendingStatusId();
    @Enumerated(EnumType.STRING)
    private Status attendingStatus;

    public AttendingStatusId getId() {
        return id;
    }

    public void setId(AttendingStatusId id) {
        this.id = id;
    }
}