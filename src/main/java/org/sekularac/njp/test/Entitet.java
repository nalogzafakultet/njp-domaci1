package org.sekularac.njp.test;

import org.sekularac.njp.annotations.classes.Entity;
import org.sekularac.njp.annotations.classes.MappedSuperclass;
import org.sekularac.njp.annotations.classes.Table;
import org.sekularac.njp.annotations.enums.EnumeratedType;
import org.sekularac.njp.annotations.enums.GenerationType;
import org.sekularac.njp.annotations.enums.TemporalType;
import org.sekularac.njp.annotations.field.*;

import java.util.Date;

@Entity
public class Entitet extends Cale {

    @Column
    private String name;

    @Column
    private String type;

    @Temporal(TemporalType.DATE)
    @Column
    private Date date;
    
    @ManyToOne
    @JoinColumn(name = "drugar_id",
            foreignKey = @ForeignKey(name = "DRUGAR_FK")
    )
    private Drugar drugar;

    @Enumerated(EnumeratedType.ORDINAL)
    private GenerationType generationType;

    public Entitet() {
        super();
        System.out.println("Instanciram entitet.");
    }

    public Entitet(Long id, String name, String type, Date date, GenerationType generationType) {
        super(id);
        this.name = name;
        this.type = type;
        this.date = date;
        this.generationType = generationType;
    }

    public GenerationType getGenerationType() {
        return generationType;
    }

    public void setGenerationType(GenerationType generationType) {
        this.generationType = generationType;
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

    public Drugar getDrugar() {
        return drugar;
    }

    public void setDrugar(Drugar drugar) {
        this.drugar = drugar;
    }

    @Override
    public String toString() {
        return "Entitet{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", date=" + date +
                ", drugar=" + drugar +
                ", generationType=" + generationType +
                '}';
    }
}
