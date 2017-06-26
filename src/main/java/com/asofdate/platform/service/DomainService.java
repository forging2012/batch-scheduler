package com.asofdate.platform.service;

import com.asofdate.platform.entity.DomainEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/1.
 */
public interface DomainService {
    JSONObject findAll(String domainId);

    List<DomainEntity> getAll();

    int update(DomainEntity domainEntity);

    String delete(JSONArray jsonArray);

    int add(DomainEntity domainEntity);

    DomainEntity getDomainDetails(String domainId);
}
