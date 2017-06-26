package com.asofdate.dispatch.service;

import com.asofdate.dispatch.entity.BatchGroupEntity;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/25.
 */
public interface BatchGroupService {
    List<BatchGroupEntity> findByBatchId(String domainId, String batchId);

    List<BatchGroupEntity> getGroup(String batchId);

    int addGroup(JSONArray jsonArray);

    int deleteGroup(JSONArray jsonArray);

    List<BatchGroupEntity> getDependency(String batchid, String id);
}
