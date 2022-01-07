package com.regg.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_loan;
    private Long id_book;
    private Long id_employee;
    private Long id_client;
    @Temporal(TemporalType.DATE)
    private Date date;
}
