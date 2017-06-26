package com.asofdate.dispatch.service;

import com.asofdate.dispatch.entity.GroupDependencyEntity;
import org.json.JSONArray;

import java.util.List;
import java.util.Set;

/**
 * Created by hzwy23 on 2017/5/27.
 */
public interface GroupDependencyService {
    List<GroupDependencyEntity> findById(String domainId, String batchId);

    void afterPropertiesSet(String domainId, String batchId);

    Set<GroupDependencyEntity> getGroupDependency(String gid);

    JSONArray getUp(String id);

    int deleteGroupDependency(String uuid);

    int addGroupDependency(JSONArray jsonArray);
}
