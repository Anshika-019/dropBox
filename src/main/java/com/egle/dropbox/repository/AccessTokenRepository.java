package com.egle.dropbox.repository;


import com.egle.dropbox.model.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTokenRepository extends JpaRepository<AccessToken, String> {}

