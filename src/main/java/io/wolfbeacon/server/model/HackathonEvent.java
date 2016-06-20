package io.wolfbeacon.server.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Aaron on 10/06/2016.
 */
@Entity
@Table(name = "hackathon_events")
public class HackathonEvent implements DomainModel<Long>{

    public enum Status {
    }

    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creatorId", nullable = false)
    private User creator;
    private String name;
    private Timestamp createdAt;
    private Timestamp lastUpdatedAt;
    private Timestamp eventDate;
    @Enumerated(EnumType.STRING)
    private Status eventStatus;
    private String eventAddress;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String eventPictureUrl;


    public HackathonEvent() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Timestamp lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Timestamp getEventDate() {
        return eventDate;
    }

    public void setEventDate(Timestamp eventDate) {
        this.eventDate = eventDate;
    }

    public Status getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(Status eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEventPictureUrl() {
        return eventPictureUrl;
    }

    public void setEventPictureUrl(String eventPictureUrl) {
        this.eventPictureUrl = eventPictureUrl;
    }

    @Override
    public String toString() {
        return "HackathonEvent{" +
                "id=" + id +
                ", creator=" + creator +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", lastUpdatedAt=" + lastUpdatedAt +
                ", eventDate=" + eventDate +
                ", eventStatus=" + eventStatus +
                ", eventAddress='" + eventAddress + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", country='" + country + '\'' +
                ", eventPictureUrl='" + eventPictureUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HackathonEvent that = (HackathonEvent) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getCreator() != null ? !getCreator().equals(that.getCreator()) : that.getCreator() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getCreatedAt() != null ? !getCreatedAt().equals(that.getCreatedAt()) : that.getCreatedAt() != null)
            return false;
        if (getLastUpdatedAt() != null ? !getLastUpdatedAt().equals(that.getLastUpdatedAt()) : that.getLastUpdatedAt() != null)
            return false;
        if (getEventDate() != null ? !getEventDate().equals(that.getEventDate()) : that.getEventDate() != null)
            return false;
        if (getEventStatus() != that.getEventStatus()) return false;
        if (getEventAddress() != null ? !getEventAddress().equals(that.getEventAddress()) : that.getEventAddress() != null)
            return false;
        if (getCity() != null ? !getCity().equals(that.getCity()) : that.getCity() != null) return false;
        if (getState() != null ? !getState().equals(that.getState()) : that.getState() != null) return false;
        if (getZip() != null ? !getZip().equals(that.getZip()) : that.getZip() != null) return false;
        if (getCountry() != null ? !getCountry().equals(that.getCountry()) : that.getCountry() != null) return false;
        return getEventPictureUrl() != null ? getEventPictureUrl().equals(that.getEventPictureUrl()) : that.getEventPictureUrl() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getCreator() != null ? getCreator().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCreatedAt() != null ? getCreatedAt().hashCode() : 0);
        result = 31 * result + (getLastUpdatedAt() != null ? getLastUpdatedAt().hashCode() : 0);
        result = 31 * result + (getEventDate() != null ? getEventDate().hashCode() : 0);
        result = 31 * result + (getEventStatus() != null ? getEventStatus().hashCode() : 0);
        result = 31 * result + (getEventAddress() != null ? getEventAddress().hashCode() : 0);
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        result = 31 * result + (getState() != null ? getState().hashCode() : 0);
        result = 31 * result + (getZip() != null ? getZip().hashCode() : 0);
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        result = 31 * result + (getEventPictureUrl() != null ? getEventPictureUrl().hashCode() : 0);
        return result;
    }
}
