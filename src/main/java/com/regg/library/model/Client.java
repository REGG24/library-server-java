package com.regg.library.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id_client;
    @NotBlank
    private String name;
    private Long phone;
    private String address;

    public Client(){

    }

    public Client(String name, Long phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public Long getId_client(){
        return this.id_client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
