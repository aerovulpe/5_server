package io.wolfbeacon.server.model;

import javax.persistence.*;

/**
 * Created by Aaron on 22/05/2016.
 */
@Entity
@Table(name = "mailing_list")
public class Email implements DomainModel<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String emailAddress;
    private String name;

    public Email() {
    }

    public Email(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Email(String emailAddress, String name) {
        this(emailAddress);
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Email fromEmailAddress(String emailAddress) {
        return new Email(emailAddress);
    }

    @Override
    public String toString() {
        return "Email{" +
                "emailAddress='" + emailAddress + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email = (Email) o;

        if (!getEmailAddress().equals(email.getEmailAddress())) return false;
        return getName() != null ? getName().equals(email.getName()) : email.getName() == null;

    }

    @Override
    public int hashCode() {
        int result = getEmailAddress().hashCode();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}
