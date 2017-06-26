package com.asofdate.dispatch.service;

import com.asofdate.dispatch.entity.GroupDefineEntity;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/1.
 */
public interface GroupDefineService {
    List<GroupDefineEntity> findAll(String domainId);

    int update(GroupDefineEntity m);

    String delete(List<GroupDefineEntity> m);

    int add(GroupDefineEntity m);

    int updateArg(String argValue, String uuid, String arg_id);
}
