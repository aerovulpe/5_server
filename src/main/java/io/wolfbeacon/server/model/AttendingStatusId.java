package io.wolfbeacon.server.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by Aaron on 14/06/2016.
 */
@Embeddable
public class AttendingStatusId implements Serializable{
    @ManyToOne
    private User user;
    @ManyToOne
    private HackathonEvent hackathonEvent;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HackathonEvent getHackathonEvent() {
        return hackathonEvent;
    }

    public void setHackathonEvent(HackathonEvent hackathonEvent) {
        this.hackathonEvent = hackathonEvent;
    }

    @Override
    public String toString() {
        return "AttendingStatusId{" +
                "user=" + user +
                ", hackathonEvent=" + hackathonEvent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttendingStatusId that = (AttendingStatusId) o;

        if (getUser() != null ? !getUser().equals(that.getUser()) : that.getUser() != null) return false;
        return getHackathonEvent() != null ? getHackathonEvent().equals(that.getHackathonEvent()) : that.getHackathonEvent() == null;

    }

    @Override
    public int hashCode() {
        int result = getUser() != null ? getUser().hashCode() : 0;
        result = 31 * result + (getHackathonEvent() != null ? getHackathonEvent().hashCode() : 0);
        return result;
    }
}
