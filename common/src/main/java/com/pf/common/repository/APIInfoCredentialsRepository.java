package com.pf.common.repository;

import com.pf.common.model.entity.APIInfoCredentials;
import com.pf.common.model.entity.CredentialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface APIInfoCredentialsRepository extends JpaRepository<APIInfoCredentials, Long> {
    APIInfoCredentials findByCredentialType(CredentialType credentialType);
}
