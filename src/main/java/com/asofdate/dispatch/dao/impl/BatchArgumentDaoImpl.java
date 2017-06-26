package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.BatchArgumentDao;
import com.asofdate.dispatch.entity.BatchArgumentEntiry;
import com.asofdate.sql.SqlDefine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/24.
 *
 * @author hzwy23
 */
@Repository
public class BatchArgumentDaoImpl implements BatchArgumentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List findAll(String domainId, String batchId) {
        RowMapper<BatchArgumentEntiry> rowMapper = new BeanPropertyRowMapper<BatchArgumentEntiry>(BatchArgumentEntiry.class);

        // 获取固定参数,任务参数,任务组参数
        List<BatchArgumentEntiry> list = jdbcTemplate.query(SqlDefine.sys_rdbms_163, rowMapper, domainId, batchId);

        //获取批次类型参数
        List<BatchArgumentEntiry> list2 = jdbcTemplate.query(SqlDefine.sys_rdbms_164, rowMapper, domainId, batchId);

        return bindAsofdate(list, list2);
    }

    @Override
    public List<BatchArgumentEntiry> findBatchArgsById(String batchId) {
        RowMapper<BatchArgumentEntiry> rowMapper = new BeanPropertyRowMapper<>(BatchArgumentEntiry.class);
        return jdbcTemplate.query(SqlDefine.sys_rdbms_139, rowMapper, batchId);
//        JSONArray jsonArray = new JSONArray();
//        String asOfDate = getAsOfDate(batchId);
//        jdbcTemplate.query(SqlDefine.sys_rdbms_139, new RowCallbackHandler() {
//            @Override
//            public void processRow(ResultSet resultSet) throws SQLException {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("code_number", resultSet.getString("code_number"));
//                jsonObject.put("batch_id", resultSet.getString("batch_id"));
//                jsonObject.put("arg_id", resultSet.getString("arg_id"));
//                jsonObject.put("domain_id", resultSet.getString("domain_id"));
//
//                if ("1".equals(resultSet.getString("bind_as_of_date"))) {
//                    jsonObject.put("arg_value", asOfDate);
//                } else {
//                    jsonObject.put("arg_value", resultSet.getString("arg_value"));
//                }
//
//                jsonObject.put("bind_as_of_date", resultSet.getString("bind_as_of_date"));
//                jsonObject.put("arg_desc", resultSet.getString("arg_desc"));
//                jsonArray.put(jsonObject);
//            }
//        }, batchId);
//
//        return jsonArray;
    }

    @Override
    public String getAsOfDate(String batchId) {
        return jdbcTemplate.queryForObject(SqlDefine.sys_rdbms_157, String.class, batchId);
    }


    @Transactional
    @Override
    public int add(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            if (isExists(jsonObject)) {
                if (1 != jdbcTemplate.update(SqlDefine.sys_rdbms_160, jsonObject.getString("arg_value"),
                        jsonObject.getString("batch_id"),
                        jsonObject.getString("arg_id"))) {
                    return -1;
                }
            } else {
                if (1 != jdbcTemplate.update(SqlDefine.sys_rdbms_158,
                        jsonObject.getString("batch_id"),
                        jsonObject.getString("arg_id"),
                        jsonObject.getString("arg_value"),
                        jsonObject.getString("domain_id"))) {
                    return -1;
                }
            }
        }
        return 1;
    }

    private List<BatchArgumentEntiry> bindAsofdate(List<BatchArgumentEntiry> ret, List<BatchArgumentEntiry> source) {
        for (BatchArgumentEntiry m : source) {
            // 绑定批次日期
            if ("1".equals(m.getBindAsOfDate())) {
                String asOfDate = jdbcTemplate.queryForObject(SqlDefine.sys_rdbms_157,
                        String.class,
                        m.getBatchId());
                m.setArgValue(asOfDate);
            }
            ret.add(m);
        }
        return ret;
    }

    private boolean isExists(JSONObject jsonObject) {
        int flag = jdbcTemplate.queryForObject(SqlDefine.sys_rdbms_159, Integer.class, jsonObject.getString("batch_id"), jsonObject.getString("arg_id"));
        if (flag >= 1) {
            return true;
        }
        return false;
    }
}
