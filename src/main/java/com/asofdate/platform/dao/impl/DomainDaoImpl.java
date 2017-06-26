package com.asofdate.platform.dao.impl;

import com.asofdate.platform.dao.DomainDao;
import com.asofdate.platform.entity.DomainEntity;
import com.asofdate.sql.SqlDefine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by hzwy23 on 2017/6/1.
 */
@Repository
public class DomainDaoImpl implements DomainDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<DomainEntity> findAll() {
        RowMapper<DomainEntity> rowMapper = new BeanPropertyRowMapper<>(DomainEntity.class);
        List<DomainEntity> list = jdbcTemplate.query(SqlDefine.sys_rdbms_118, rowMapper);
        return list;
    }

    @Override
    public List<DomainEntity> getAll() {
        RowMapper<DomainEntity> rowMapper = new BeanPropertyRowMapper<>(DomainEntity.class);
        List<DomainEntity> list = jdbcTemplate.query(SqlDefine.sys_rdbms_025, rowMapper);
        return list;
    }

    @Override
    public int update(DomainEntity domainEntity) {
        return jdbcTemplate.update(SqlDefine.sys_rdbms_038,
                domainEntity.getDomain_desc(),
                domainEntity.getDomain_status_id(),
                domainEntity.getDomain_modify_user(),
                domainEntity.getDomain_id());
    }

    @Transactional
    @Override
    public String delete(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            String domainId = ((JSONObject) jsonArray.get(i)).getString("domain_id");
            jdbcTemplate.update(SqlDefine.sys_rdbms_037, domainId);
        }
        return "success";
    }

    @Override
    public int add(DomainEntity domainEntity) {
        return jdbcTemplate.update(SqlDefine.sys_rdbms_036,
                domainEntity.getDomain_id(),
                domainEntity.getDomain_desc(),
                domainEntity.getDomain_status_id(),
                domainEntity.getCreate_user_id(),
                domainEntity.getDomain_modify_user());
    }

    @Override
    public DomainEntity getDomainDetails(String domainId) {
        DomainEntity domainEntity = new DomainEntity();
        jdbcTemplate.query(SqlDefine.sys_rdbms_084, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                domainEntity.setDomain_id(domainId);
                domainEntity.setDomain_desc(resultSet.getString("domain_desc"));
                domainEntity.setDomain_status_desc(resultSet.getString("domain_status_desc"));
                domainEntity.setMaintance_date(resultSet.getString("maintance_date"));
                domainEntity.setCreate_user_id(resultSet.getString("create_user_id"));
                domainEntity.setDomain_modify_user(resultSet.getString("domain_modify_user"));
                domainEntity.setDomain_modify_date(resultSet.getString("domain_modify_date"));
            }
        }, domainId);
        return domainEntity;
    }
}
