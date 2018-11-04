package org.sekularac.njp.test;

import org.sekularac.njp.annotations.classes.Entity;
import org.sekularac.njp.annotations.classes.MappedSuperclass;
import org.sekularac.njp.annotations.classes.Table;
import org.sekularac.njp.annotations.enums.TemporalType;
import org.sekularac.njp.annotations.field.Column;
import org.sekularac.njp.annotations.field.Id;
import org.sekularac.njp.annotations.field.Temporal;

import java.util.Date;

@Entity
public class Entitet extends Cale {

    @Id
    private Long id;

    @Column
    private String name;

    @Column(name = "Nemanja")
    private String type;

    @Temporal(TemporalType.DATE)
    private Date date;

    public Entitet() {
        System.out.println("Instanciram entitet.");
    }

    public Entitet(long id, String name, String type, Date date) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
