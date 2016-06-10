package io.wolfbeacon.server.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Aaron on 03/06/2016.
 */
@Entity
@Table(name = "users")
public class User implements DomainModel<Long> {
    @GenericGenerator(name = "generator", strategy = "foreign",
            parameters = @Parameter(name = "property", value = "account"))
    @Id
    @GeneratedValue(generator = "generator")
    private Long id;
    @OneToOne
    @JoinColumn(name = "id")
    private Account account;
    private Timestamp createdAt;
    private Timestamp lastUpdatedAt;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", accountId=" + account +
                ", createdAt=" + createdAt +
                ", lastUpdatedAt=" + lastUpdatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (getId() != null ? !getId().equals(user.getId()) : user.getId() != null) return false;
        if (getAccount() != null ? !getAccount().equals(user.getAccount()) : user.getAccount() != null)
            return false;
        if (getCreatedAt() != null ? !getCreatedAt().equals(user.getCreatedAt()) : user.getCreatedAt() != null)
            return false;
        return getLastUpdatedAt() != null ? getLastUpdatedAt().equals(user.getLastUpdatedAt()) : user.getLastUpdatedAt() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getAccount() != null ? getAccount().hashCode() : 0);
        result = 31 * result + (getCreatedAt() != null ? getCreatedAt().hashCode() : 0);
        result = 31 * result + (getLastUpdatedAt() != null ? getLastUpdatedAt().hashCode() : 0);
        return result;
    }
}
