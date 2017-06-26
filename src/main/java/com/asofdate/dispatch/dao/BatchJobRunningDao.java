package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.entity.BatchJobStatusEntity;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/17.
 */
public interface BatchJobRunningDao {
    List<BatchJobStatusEntity> findAll(String batchId, String gid);

    BatchJobStatusEntity getDetails(String batchId, String gid, String tid);
}
