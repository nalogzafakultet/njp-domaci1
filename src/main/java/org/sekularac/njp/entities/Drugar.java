package org.sekularac.njp.entities;

import org.sekularac.njp.annotations.classes.Entity;
import org.sekularac.njp.annotations.classes.Table;
import org.sekularac.njp.annotations.field.Column;
import org.sekularac.njp.annotations.field.GeneratedValue;
import org.sekularac.njp.annotations.field.Id;

@Entity
@Table(name = "DRUGAR")
public class Drugar {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    public Drugar() {

    }

    public Drugar(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
