package com.pf.karza.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "response", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Response extends BaseId {
    @Column(name = "request_id", nullable = false, unique = true)
    private String requestId; //from karza

    @Lob
    private String pdfDownloadLink;

    @Lob
    private String excelDownloadLink;

    @Column(name = "mas_ref_id", nullable = false, unique = true)
    private String masRefId;

    private Integer statusCode;

    @OneToOne
    @JoinColumn(nullable = false, name = "request_ref_id")
    @JsonBackReference
    private UserRequest userRequest;
}
