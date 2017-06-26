package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.dao.TaskArgumentDao;
import com.asofdate.dispatch.dao.TaskDefineDao;
import com.asofdate.dispatch.entity.GroupTaskEntity;
import com.asofdate.dispatch.entity.TaskDefineEntity;
import com.asofdate.dispatch.service.GroupTaskService;
import com.asofdate.dispatch.service.TaskDefineService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzwy23 on 2017/5/24.
 */
@Service
public class TaskDefineServiceImpl implements TaskDefineService {
    @Autowired
    private TaskDefineDao dispatchTaskDefineDao;

    @Autowired
    private GroupTaskService groupTaskService;

    @Autowired
    private TaskArgumentDao taskArgumentDao;

    @Override
    public List<TaskDefineEntity> findAll(String domainId, String batchId) {
        List<TaskDefineEntity> list = dispatchTaskDefineDao.findAll(domainId);
        List<GroupTaskEntity> groupTaskEntityList = groupTaskService.findByBatchId(domainId, batchId);
        Map<String, GroupTaskEntity> map = new HashMap<String, GroupTaskEntity>();

        for (GroupTaskEntity m : groupTaskEntityList) {
            if (!map.containsKey(m.getTaskId())) {
                map.put(m.getTaskId(), m);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            if (!map.containsKey(list.get(i).getTaskId())) {
                list.remove(i);
                i--;
            }
        }

        return list;
    }

    @Override
    public List<TaskDefineEntity> getAll(String domainId) {
        return dispatchTaskDefineDao.findAll(domainId);
    }

    @Override
    public int add(TaskDefineEntity m) {
        return dispatchTaskDefineDao.add(m);
    }

    @Override
    public String delete(List<TaskDefineEntity> m) {
        return dispatchTaskDefineDao.delete(m);
    }

    @Override
    public int update(TaskDefineEntity m) {
        return dispatchTaskDefineDao.update(m);
    }

    @Override
    public JSONArray getTaskArg(String taskId) {
        return taskArgumentDao.getTaskArg(taskId);
    }

    @Override
    public int updateArgumentSort(String sortId, String uuid) {
        return taskArgumentDao.updateSort(sortId, uuid);
    }

    @Override
    public int deleteArg(String uuid) {
        return taskArgumentDao.deleteArg(uuid);
    }

    @Override
    public JSONObject getArgType(String argId) {
        return taskArgumentDao.getArgType(argId);
    }

    @Override
    public int addArg(JSONObject jsonObject) {
        return taskArgumentDao.addArg(jsonObject);
    }

    @Override
    public int updateArgValue(String argValue, String uuid) {
        return taskArgumentDao.updateArgValue(argValue, uuid);
    }
}
