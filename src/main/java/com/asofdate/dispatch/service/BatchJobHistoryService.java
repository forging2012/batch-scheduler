package com.asofdate.dispatch.service;

import com.asofdate.dispatch.entity.BatchJobHistoryEntity;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/17.
 */
public interface BatchJobHistoryService {
    List<BatchJobHistoryEntity> findAll(String id, String gid);
}
