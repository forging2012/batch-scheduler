package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.entity.BatchGroupStatusEntity;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/17.
 */
public interface BatchGroupRunningDao {
    List<BatchGroupStatusEntity> findAll(String domainId);

    Integer getRatio(String batchId, String gid);

    BatchGroupStatusEntity getDetails(String batchId, String gid);
}
