package com.regg.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "returnss")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Return {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_return;
    private Long id_loan;
    @Temporal(TemporalType.DATE)
    private Date date;
}
