package com.asofdate.dispatch.service;

import com.asofdate.dispatch.entity.TaskDefineEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/24.
 */
public interface TaskDefineService {
    List<TaskDefineEntity> findAll(String domainId, String batchId);

    List<TaskDefineEntity> getAll(String domainId);

    int add(TaskDefineEntity m);

    String delete(List<TaskDefineEntity> m);

    int update(TaskDefineEntity m);

    JSONArray getTaskArg(String taskId);

    int updateArgumentSort(String sortId, String uuid);

    int deleteArg(String uuid);

    JSONObject getArgType(String argId);

    int addArg(JSONObject jsonObject);

    int updateArgValue(String argValue, String uuid);
}
