package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "relationship_details_vec", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class RelationshipDetailsVec extends BaseID {
	private String message;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "relationship_details_vec_id")
	private List<RelationshipDetails> relationshipDetails;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "relationship_dispute_id")
	private DisputeRemarks relationshipDispute;
}