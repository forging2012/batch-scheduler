package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.entity.BatchHistoryEntity;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/16.
 */
public interface BatchHistoryDao {
    List<BatchHistoryEntity> findAll(String domainId);

    int delete(List<BatchHistoryEntity> list);
}
