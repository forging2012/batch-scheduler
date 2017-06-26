package com.asofdate.platform.dao;

import com.asofdate.platform.entity.DomainEntity;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/1.
 */
public interface DomainDao {
    List<DomainEntity> findAll();

    List<DomainEntity> getAll();

    int update(DomainEntity domainEntity);

    String delete(JSONArray jsonArray);

    int add(DomainEntity domainEntity);

    DomainEntity getDomainDetails(String domainId);
}
