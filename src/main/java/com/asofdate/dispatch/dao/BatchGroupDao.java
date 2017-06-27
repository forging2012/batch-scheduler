package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.entity.BatchGroupEntity;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/24.
 */
public interface BatchGroupDao {
    List findAll(String domainId);

    List<BatchGroupEntity> getGroup(String batchId);

    int addGroup(List<BatchGroupEntity> list);

    int deleteGroup(List<BatchGroupEntity> list);

    List<BatchGroupEntity> getDependency(String batchid, String id);
}
