package com.asofdate.platform.dao;

import com.asofdate.platform.entity.RoleEntity;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
public interface RoleDao {
    List<RoleEntity> findAll(String domainId);

    RoleEntity getDetails(String roleId);

    List<RoleEntity> getOther(String userId);

    List<RoleEntity> getOwner(String userId);

    int auth(JSONArray jsonArray, String modifyUserId);

    int revoke(JSONArray jsonArray);

    int batchAuth(JSONArray jsonArray, String modifyUserId);

    int add(RoleEntity roleEntity);

    int delete(JSONArray jsonArray);

    int update(RoleEntity roleEntity);
}
