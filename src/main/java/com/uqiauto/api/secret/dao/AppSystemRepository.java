package com.uqiauto.api.secret.dao;

import com.uqiauto.api.secret.model.AppSystem;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface AppSystemRepository  extends JpaRepository<AppSystem,Long>{

    Optional<AppSystem> getAppSystemByAppKey(String appKey);

}
