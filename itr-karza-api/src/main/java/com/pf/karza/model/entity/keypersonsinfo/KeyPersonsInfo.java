package com.pf.karza.model.entity.keypersonsinfo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.UserRequest;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "key_persons_info", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyPersonsInfo extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "request_ref_id")
    private UserRequest userRequest;

    @JsonManagedReference
    @OneToMany(mappedBy = "keyPersonsInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("shareHolderInfo")
    private List<ShareHolderInfo> shareHolderInfoList;

    @JsonManagedReference
    @OneToMany(mappedBy = "keyPersonsInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("keyPersons")
    private List<KeyPersons> keyPersonsList;

}