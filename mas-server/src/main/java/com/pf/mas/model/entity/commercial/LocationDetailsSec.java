package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "location_details_sec", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class LocationDetailsSec extends BaseID {
	private String message;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "location_details_sec_id")
	private List<LocationInformation> locationInformationList;
	private String contactNumber;
	private String faxNumber;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "location_dispute_id")
	private DisputeRemarks locationDispute;
}
