package com.asofdate.dispatch.service;

import com.asofdate.dispatch.entity.GroupTaskEntity;
import com.asofdate.dispatch.entity.TaskDependencyEntity;
import org.json.JSONArray;

import java.util.List;
import java.util.Set;

/**
 * Created by hzwy23 on 2017/5/27.
 */
public interface TaskDependencyService {
    List<TaskDependencyEntity> findById(String domainId, String batchId);

    Set<String> getTaskDependency(String gid, String id);

    void afterPropertiesSet(String domainId, String batchId);

    List<GroupTaskEntity> getTaskDependency(String id);

    List<GroupTaskEntity> getGroupTask(String groupId, String id);

    int addTaskDependency(JSONArray jsonArray);

    int deleteTaskDependency(String uuid);
}
