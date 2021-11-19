package app.entities;


import com.sun.istack.NotNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Company implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name="address")
    private String address;

    @NotNull
    @Column(name = "nip")
    private String nip;

    @OneToMany(mappedBy = "company", orphanRemoval = true)
    private List<Employee> employees;

    public Company(String id, String name, String address, String nip) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.nip = nip;
    }

    public Company(String name, String address, String nip){
        this.name = name;
        this.address = address;
        this.nip = nip;
    }

    public Company(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
