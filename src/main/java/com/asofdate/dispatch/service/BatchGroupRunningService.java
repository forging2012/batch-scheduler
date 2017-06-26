package com.asofdate.dispatch.service;

import com.asofdate.dispatch.entity.BatchGroupStatusEntity;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/17.
 */
public interface BatchGroupRunningService {
    List<BatchGroupStatusEntity> findAll(String batchId);

    Integer getRatio(String batchId, String gid);

    BatchGroupStatusEntity getDetails(String batchId, String gid);
}
