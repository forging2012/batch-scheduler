package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.BatchGroupDao;
import com.asofdate.dispatch.entity.BatchGroupEntity;
import com.asofdate.dispatch.entity.GroupDependencyEntity;
import com.asofdate.sql.SqlDefine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by hzwy23 on 2017/5/24.
 */
@Repository
public class BatchGroupDaoImpl implements BatchGroupDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List findAll(String domainId) {
        RowMapper<BatchGroupEntity> rowMapper = new BeanPropertyRowMapper<>(BatchGroupEntity.class);
        List<BatchGroupEntity> list = jdbcTemplate.query(SqlDefine.sys_rdbms_106, rowMapper, domainId);
        return list;
    }

    @Override
    public List<BatchGroupEntity> getGroup(String batchId) {
        RowMapper<BatchGroupEntity> rowMapper = new BeanPropertyRowMapper<>(BatchGroupEntity.class);
        return jdbcTemplate.query(SqlDefine.sys_rdbms_137, rowMapper, batchId);
    }

    @Override
    public int addGroup(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String id = UUID.randomUUID().toString();
            String batch_id = jsonObject.getString("batch_id");
            String group_id = jsonObject.getString("group_id");
            String domain_id = jsonObject.getString("domain_id");

            if (1 != jdbcTemplate.update(SqlDefine.sys_rdbms_154,
                    id, batch_id, group_id, domain_id)) {
                return -1;
            }
        }
        return 1;
    }

    @Transactional
    @Override
    public int deleteGroup(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String id = jsonObject.getString("id");
            if (1 != jdbcTemplate.update(SqlDefine.sys_rdbms_155, id)) {
                return -1;
            }
        }
        return 1;
    }

    @Override
    public List<BatchGroupEntity> getDependency(String batchid, String id) {
        List<BatchGroupEntity> list = getGroup(batchid);
        Set<String> set = getChildren(batchid, id);

        for (int i = 0; i < list.size(); i++) {
            String sub = list.get(i).getId();
            if (set.contains(sub)) {
                list.remove(i);
                i--;
            }
        }
        return list;
    }

    private Set<String> getChildren(String batchId, String id) {
        RowMapper<GroupDependencyEntity> rowMapper = new BeanPropertyRowMapper<>(GroupDependencyEntity.class);
        List<GroupDependencyEntity> list = jdbcTemplate.query(SqlDefine.sys_rdbms_073, rowMapper, batchId);

        Set<String> set = new HashSet<>();
        children(list, id, set);
        set.add(id);

        for (BatchGroupEntity m : getOwner(id)) {
            set.add(m.getUpId());
        }
        return set;
    }

    private List<BatchGroupEntity> getOwner(String id) {
        RowMapper<BatchGroupEntity> rowMapper = new BeanPropertyRowMapper<>(BatchGroupEntity.class);
        return jdbcTemplate.query(SqlDefine.sys_rdbms_138, rowMapper, id);
    }

    private void children(List<GroupDependencyEntity> all, String id, Set<String> set) {
        for (GroupDependencyEntity m : all) {
            String upId = m.getUpId();
            if (upId == null || set.contains(m.getId())) {
                continue;
            }
            if (id.equals(upId)) {
                set.add(m.getId());
                children(all, m.getId(), set);
            }
        }
    }
}
