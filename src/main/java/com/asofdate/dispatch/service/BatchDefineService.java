package com.asofdate.dispatch.service;

import com.asofdate.dispatch.dto.BatchArgumentDTO;
import com.asofdate.dispatch.entity.BatchDefineEntity;
import com.asofdate.utils.RetMsg;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/1.
 *
 * @author hzwy23
 */
public interface BatchDefineService {
    /**
     * 查询指定域中所有的批次信息
     *
     * @param domainId
     * @return List 返回这个域中，所有定义的批次列表信息
     */
    List<BatchDefineEntity> findAll(String domainId);

    /**
     * 查询某个域中所有运行中的批次列表信息
     *
     * @param domainId
     * @return List 返回所有运行中的批次信息
     */
    List<BatchDefineEntity> getRunning(String domainId);

    /**
     * 新增批次
     *
     * @param vo
     * @return RetMsg 当添加成功后，返回添加的行数
     */
    RetMsg addBatch(BatchDefineEntity vo);

    /**
     * 删除批次信息
     *
     * @param list 需要删除的批次列表信息
     * @return RetMsg 删除操作状态信息
     */
    RetMsg deleteBatch(List<BatchDefineEntity> list);

    /**
     * 更新批次信息
     *
     * @param vo 需要更新的批次信息
     * @return 返回更新操作的结果
     */
    RetMsg updateBatch(BatchDefineEntity vo);

    /**
     * 查询批次状态
     *
     * @param batchId
     * @return 返回批次状态值
     */
    int getStatus(String batchId);

    /**
     * 设置批次状态值
     *
     * @param batchId
     * @param status
     * @return 返回更新批次状态的操作结果
     */
    RetMsg setStatus(String batchId, int status);

    RetMsg runBatchInit(String batchId);

    int batchPagging(String batchid);

    int updateAsofdate(String asofdate, String batchId);

    /**
     * 根据批次号，查询批次所拥有的批次类型的参数
     *
     * @param batchId
     * @return 返回这个批次所拥有的配词类型的参数，如果参数绑定了批次日期，表示参数值与批次日期相等
     */
    List<BatchArgumentDTO> findBatchArgsById(String batchId);

    // 根据批次号,向这个批次中,给批次参数赋值
    int addBatchArg(JSONArray jsonArray);

    JSONObject getBatchCompletedRadio(String batchId);

    int destoryBatch(String batchId, String retMsg, int Status);

    int saveHistory(String batchId);
}
