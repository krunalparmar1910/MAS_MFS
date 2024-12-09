package com.pf.mas.model.entity.cibil;

import com.pf.mas.model.constant.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "interest_master", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EMIMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long interestId;
    @Column(name = "type_of_loan")
    private String typeOfLoan;
    @Column(name = "interest")
    private int interest;
    @Column(name = "tenure")
    private int tenure;
}
