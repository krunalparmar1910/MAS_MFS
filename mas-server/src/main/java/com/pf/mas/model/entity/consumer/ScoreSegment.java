package com.pf.mas.model.entity.consumer;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "score_segment", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ScoreSegment extends BaseID {
    private Long length;
    private String scoreName;
    private String scoreCardName;
    private Long scoreCardVersion;
    private Date scoreDate;
    private Long score;
    @Column(name = "exclusion_code_1")
    private String exclusionCode1;
    @Column(name = "exclusion_code_2")
    private String exclusionCode2;
    @Column(name = "exclusion_code_3")
    private String exclusionCode3;
    @Column(name = "exclusion_code_4")
    private String exclusionCode4;
    @Column(name = "exclusion_code_5")
    private String exclusionCode5;
    @Column(name = "exclusion_code_6")
    private String exclusionCode6;
    @Column(name = "exclusion_code_7")
    private String exclusionCode7;
    @Column(name = "exclusion_code_8")
    private String exclusionCode8;
    @Column(name = "exclusion_code_9")
    private String exclusionCode9;
    @Column(name = "exclusion_code_10")
    private String exclusionCode10;
    @Column(name = "reason_code_1")
    private String reasonCode1;
    @Column(name = "reason_code_2")
    private String reasonCode2;
    @Column(name = "reason_code_3")
    private String reasonCode3;
    @Column(name = "reason_code_4")
    private String reasonCode4;
    @Column(name = "reason_code_5")
    private String reasonCode5;
    private String errorCode;
    private String trlScore;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "score_segment_id")
    private List<ScoreSegmentBureauCharacteristics> scoreSegmentBureauCharacteristicsList;

}
