package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.dao.BatchArgumentDao;
import com.asofdate.dispatch.dao.BatchDefineDao;
import com.asofdate.dispatch.dao.BatchJobStatusDao;
import com.asofdate.dispatch.dto.BatchArgumentDTO;
import com.asofdate.dispatch.entity.BatchArgumentEntiry;
import com.asofdate.dispatch.entity.BatchDefineEntity;
import com.asofdate.dispatch.service.BatchDefineService;
import com.asofdate.utils.RetMsg;
import com.asofdate.utils.SysStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzwy23 on 2017/6/1.
 */
@Service
public class BatchDefineServiceImpl implements BatchDefineService {
    private final Logger logger = LoggerFactory.getLogger(BatchDefineServiceImpl.class);
    @Autowired
    private BatchDefineDao batchDefineDao;

    @Autowired
    private BatchArgumentDao batchArgumentDao;

    @Autowired
    private BatchJobStatusDao batchJobStatusDao;

    @Override
    public List<BatchDefineEntity> findAll(String domainId) {
        return batchDefineDao.findAll(domainId);
    }

    @Override
    public List<BatchDefineEntity> getRunning(String domainId) {
        return batchDefineDao.getRunning(domainId);
    }

    @Override
    public RetMsg addBatch(BatchDefineEntity vo) {
        try {
            int size = batchDefineDao.add(vo);
            if (1 != size) {
                return new RetMsg(SysStatus.ERROR_CODE, "新增批次失败，请联系管理员", size);
            }
            return new RetMsg(SysStatus.SUCCESS_CODE, "success", null);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new RetMsg(SysStatus.EXCEPTION_ERROR_CODE, e.getMessage(), vo);
        }
    }

    @Override
    public RetMsg deleteBatch(List<BatchDefineEntity> vo) {
        try {
            String msg = batchDefineDao.delete(vo);
            if ("success".equals(msg)) {
                return new RetMsg(SysStatus.SUCCESS_CODE, "success", null);
            }
            return new RetMsg(SysStatus.ERROR_CODE, "删除批次信息失败", vo);
        } catch (Exception e) {
            return new RetMsg(SysStatus.EXCEPTION_ERROR_CODE, e.getMessage(), vo);
        }
    }

    @Override
    public RetMsg updateBatch(BatchDefineEntity m) {
        try {
            int size = batchDefineDao.update(m);
            if (1 == size) {
                return new RetMsg(SysStatus.SUCCESS_CODE, "success", null);
            }
            return new RetMsg(SysStatus.ERROR_CODE, "更新批次信息失败，请联系管理员", m);
        } catch (Exception e) {
            return new RetMsg(SysStatus.EXCEPTION_ERROR_CODE, e.getMessage(), m);
        }
    }

    @Override
    public int getStatus(String batchId) {
        return batchDefineDao.getStatus(batchId);
    }

    @Override
    public RetMsg setStatus(String batchId, int status) {
        try {
            int size = batchDefineDao.setStatus(batchId, status);
            if (1 == size) {
                return new RetMsg(SysStatus.SUCCESS_CODE, "success", null);
            }
            return new RetMsg(SysStatus.ERROR_CODE, "设置批次状态信息失败，请联系管理员", batchId);
        } catch (Exception e) {
            return new RetMsg(SysStatus.EXCEPTION_ERROR_CODE, e.getMessage(), batchId);
        }
    }

    @Override
    public RetMsg runBatchInit(String batchId) {
        try {
            int size = batchDefineDao.runBatchInit(batchId);
            if (1 == size) {
                return new RetMsg(SysStatus.SUCCESS_CODE, "success", null);
            }
            return new RetMsg(SysStatus.ERROR_CODE, "批次初始化失败，批次无法启动", null);
        } catch (Exception e) {
            return new RetMsg(SysStatus.EXCEPTION_ERROR_CODE, e.getMessage(), null);
        }
    }

    @Override
    public int batchPagging(String batchid) {
        return batchDefineDao.batchPagging(batchid);
    }

    @Override
    public int updateAsofdate(String asofdate, String batchId) {
        return batchDefineDao.updateAsofdate(asofdate, batchId);
    }

    @Override
    public List<BatchArgumentDTO> findBatchArgsById(String batchId) {
        List<BatchArgumentEntiry> batchArgList = batchArgumentDao.findBatchArgsById(batchId);
        String asOfDate = batchArgumentDao.getAsOfDate(batchId);
        String flag;

        List<BatchArgumentDTO> list = new ArrayList<>();

        for (BatchArgumentEntiry batchArg : batchArgList) {
            BatchArgumentDTO ret = new BatchArgumentDTO();
            flag = batchArg.getBindAsOfDate();
            if ("1".equals(flag)) {
                batchArg.setArgValue(asOfDate);
            }
            ret.setCodeNumber(batchArg.getCodeNumber());
            ret.setDomainId(batchArg.getDomainId());
            ret.setArgId(batchArg.getArgId());
            ret.setBatchId(batchId);
            ret.setArgValue(batchArg.getArgValue());
            ret.setArgDesc(batchArg.getArgDesc());
            ret.setBindAsOfDate(flag);
            list.add(ret);
        }
        return list;
    }

    @Override
    public int addBatchArg(JSONArray jsonArray) {
        return batchArgumentDao.add(jsonArray);
    }

    @Override
    public JSONObject getBatchCompletedRadio(String batchId) {
        int completedCnt = batchJobStatusDao.getCompletedCnt(batchId);
        int totalCnt = batchJobStatusDao.getTotalCnt(batchId);
        String asOfDate = batchDefineDao.getBatchAsOfDate(batchId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("as_of_date", asOfDate);
        if (totalCnt == 0) {
            jsonObject.put("ratio", 0);
            return jsonObject;
        }
        float radio = (float) completedCnt / (float) totalCnt;
        jsonObject.put("ratio", radio);
        return jsonObject;

    }

    @Override
    public int destoryBatch(String batchId, String retMsg, int status) {
        return batchDefineDao.destoryBatch(batchId, retMsg, status);
    }

    @Override
    public int saveHistory(String batchId) {
        return batchDefineDao.saveHistory(batchId);
    }
}
