package com.pf.common.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "api_info_credentials", schema = "mas_datastore_gst")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class APIInfoCredentials implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "credential_type")
    private CredentialType credentialType;

    @Column(name = "api_key")
    @Lob
    private String apiKey;

    @Column(name = "api_version")
    @Lob
    private String apiVersion;

    @Column(name = "base_api")
    @Lob
    private String baseApi;

    @Column(name = "base_url")
    @Lob
    private String baseUrl;

    @Column(name = "webhook_url")
    @Lob
    private String webhookUrl;
}
