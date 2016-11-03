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
    private Hackathon hackathon;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Hackathon getHackathon() {
        return hackathon;
    }

    public void setHackathon(Hackathon hackathon) {
        this.hackathon = hackathon;
    }

    @Override
    public String toString() {
        return "AttendingStatusId{" +
                "user=" + user +
                ", hackathon=" + hackathon +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttendingStatusId that = (AttendingStatusId) o;

        if (getUser() != null ? !getUser().equals(that.getUser()) : that.getUser() != null) return false;
        return getHackathon() != null ? getHackathon().equals(that.getHackathon()) : that.getHackathon() == null;

    }

    @Override
    public int hashCode() {
        int result = getUser() != null ? getUser().hashCode() : 0;
        result = 31 * result + (getHackathon() != null ? getHackathon().hashCode() : 0);
        return result;
    }
}
