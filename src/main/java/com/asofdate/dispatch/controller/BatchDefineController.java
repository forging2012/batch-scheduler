package com.asofdate.dispatch.controller;

import com.asofdate.dispatch.dto.BatchArgumentDTO;
import com.asofdate.dispatch.entity.BatchDefineEntity;
import com.asofdate.dispatch.entity.BatchGroupEntity;
import com.asofdate.dispatch.service.ArgumentService;
import com.asofdate.dispatch.service.BatchDefineService;
import com.asofdate.dispatch.service.BatchGroupService;
import com.asofdate.dispatch.service.GroupDependencyService;
import com.asofdate.dispatch.utils.BatchStatus;
import com.asofdate.platform.authentication.JwtService;
import com.asofdate.utils.Hret;
import com.asofdate.utils.JoinCode;
import com.asofdate.utils.RetMsg;
import com.asofdate.utils.SysStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzwy23 on 2017/6/1.
 */
@RestController
@RequestMapping(value = "/v1/dispatch/batch/define")
public class BatchDefineController {

    private static Logger logger = LoggerFactory.getLogger(BatchDefineController.class);
    @Autowired
    private BatchDefineService batchDefineService;
    @Autowired
    private BatchGroupService batchGroupService;
    @Autowired
    private GroupDependencyService groupDependencyService;
    @Autowired
    private ArgumentService argumentService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<BatchDefineEntity> getAll(HttpServletRequest request) {
        String domainId = request.getParameter("domain_id");
        if (domainId == null) {
            JSONObject jsonObject = JwtService.getConnectUser(request);
            domainId = jsonObject.getString("DomainId");
        }
        return batchDefineService.findAll(domainId);
    }

    // 新增任务组
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String add(HttpServletResponse response, HttpServletRequest request) {
        RetMsg retMsg = batchDefineService.addBatch(parse(request));
        if (SysStatus.SUCCESS_CODE != retMsg.getCode()) {
            response.setStatus(421);
            return Hret.error(retMsg);
        }
        return Hret.success(retMsg);
    }

    /*
    * 删除任务组
    * */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(HttpServletResponse response, HttpServletRequest request) {
        List<BatchDefineEntity> args = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(request.getParameter("JSON"));

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String batchId = jsonObject.getString("batch_id");
            String domainId = jsonObject.getString("domain_id");
            if (BatchStatus.BATCH_STATUS_RUNNING == batchDefineService.getStatus(batchId)) {
                response.setStatus(421);
                return Hret.error(500, "批次正在运行中,无法被删除", null);
            }

            BatchDefineEntity batchDefineEntity = new BatchDefineEntity();
            batchDefineEntity.setBatchId(batchId);
            batchDefineEntity.setDomainId(domainId);
            args.add(batchDefineEntity);
        }

        RetMsg msg = batchDefineService.deleteBatch(args);
        if (SysStatus.SUCCESS_CODE == msg.getCode()) {
            return Hret.success(msg);
        }

        response.setStatus(421);
        return Hret.error(msg);
    }

    /**
     * 更新任务组
     *
     * @return 返回更新操作状态信息
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String update(HttpServletResponse response, HttpServletRequest request) {
        BatchDefineEntity m = parse(request);
        if (batchDefineService.getStatus(m.getBatchId()) == BatchStatus.BATCH_STATUS_RUNNING) {
            response.setStatus(421);
            return Hret.error(421, "批次正在运行中,无法编辑", JSONObject.NULL);
        }
        RetMsg retMsg = batchDefineService.updateBatch(m);

        if (retMsg.getCode() != SysStatus.SUCCESS_CODE) {
            logger.info(retMsg.toString());
            response.setStatus(422);
            return Hret.error(retMsg);
        }
        return Hret.success(retMsg);
    }

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    @ResponseBody
    public List<BatchGroupEntity> getGroup(HttpServletRequest request) {
        String batchId = request.getParameter("batch_id");
        return batchGroupService.getGroup(batchId);
    }

    @RequestMapping(value = "/stop", method = RequestMethod.POST)
    @ResponseBody
    public String stop(HttpServletResponse response, HttpServletRequest request) {
        String batchId = request.getParameter("batch_id");
        RetMsg retMsg = batchDefineService.setStatus(batchId, 4);
        if (SysStatus.SUCCESS_CODE != retMsg.getCode()) {
            response.setStatus(422);
            return Hret.error(retMsg);
        }
        return Hret.success(retMsg);
    }

    @RequestMapping(value = "/group", method = RequestMethod.POST)
    @ResponseBody
    public String addGroup(HttpServletRequest request) {
        String batchId = request.getParameter("batch_id");
        String domainId = request.getParameter("domain_id");
        String JSON = request.getParameter("JSON");
        JSONArray jsonArray = new JSONArray(JSON);
        JSONArray arg = new JSONArray();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            jsonObject.put("domain_id", domainId);
            jsonObject.put("batch_id", batchId);
            arg.put(jsonObject);
        }
        if (1 != batchGroupService.addGroup(arg)) {
            return Hret.error(421, "添加任务组失败", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    @RequestMapping(value = "/group/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteGroup(HttpServletRequest request) {
        String id = request.getParameter("id");
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonArray.put(jsonObject);
        if (1 != batchGroupService.deleteGroup(jsonArray)) {
            return Hret.error(421, "删除任务组失败", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    @RequestMapping(value = "/group/list/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteGroupList(HttpServletResponse response, HttpServletRequest request) {
        JSONArray jsonArray = new JSONArray(request.getParameter("JSON"));
        try {
            int size = batchGroupService.deleteGroup(jsonArray);
            if (1 != size) {
                response.setStatus(421);
                return Hret.error(421, "删除任务组失败,任务组已经被当做上级任务依赖引用,请先删除引用关系", JSONObject.NULL);
            }
            return Hret.success(200, "success", JSONObject.NULL);
        } catch (Exception e) {
            response.setStatus(421);
            return Hret.error(421, "删除任务组失败,任务组已经被当做上级任务依赖引用,请先删除引用关系", e.getMessage());
        }
    }


    @RequestMapping(value = "/group/dependency", method = RequestMethod.GET)
    @ResponseBody
    public String getDependency(HttpServletRequest request) {
        String id = request.getParameter("id");
        return groupDependencyService.getUp(id).toString();
    }

    @RequestMapping(value = "/group/dependency/add", method = RequestMethod.GET)
    @ResponseBody
    public List<BatchGroupEntity> canAddDependency(HttpServletRequest request) {
        String batchId = request.getParameter("batch_id");
        String id = request.getParameter("id");
        return batchGroupService.getDependency(batchId, id);
    }

    @RequestMapping(value = "/group/dependency", method = RequestMethod.POST)
    @ResponseBody
    public String addDependency(HttpServletResponse response, HttpServletRequest request) {

        String JSON = request.getParameter("JSON");
        JSONArray jsonArray = new JSONArray(JSON);

        if (1 != groupDependencyService.addGroupDependency(jsonArray)) {
            response.setStatus(421);
            return Hret.success(421, "新增任务依赖失败", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    @RequestMapping(value = "/group/dependency/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteGroupDependency(HttpServletResponse response, HttpServletRequest request) {
        String uuid = request.getParameter("uuid");
        if (1 != groupDependencyService.deleteGroupDependency(uuid)) {
            response.setStatus(421);
            return Hret.error(421, "删除任务组依赖关系失败,请联系管理员", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    @RequestMapping(value = "/argument", method = RequestMethod.GET)
    @ResponseBody
    public List<BatchArgumentDTO> getBatchArg(HttpServletRequest request) {
        String id = request.getParameter("batch_id");
        return batchDefineService.findBatchArgsById(id);
    }

    @RequestMapping(value = "/argument", method = RequestMethod.POST)
    @ResponseBody
    public String addBatchArg(HttpServletRequest request) {
        JSONArray jsonArray = new JSONArray(request.getParameter("JSON"));
        if (1 != batchDefineService.addBatchArg(jsonArray)) {
            return Hret.error(421, "添加批次参数失败", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    @RequestMapping(value = "/asofdate", method = RequestMethod.PUT)
    @ResponseBody
    public String updateAsofdate(HttpServletResponse response, HttpServletRequest request) {
        String batchId = request.getParameter("batch_id");
        String asofdate = request.getParameter("as_of_date");
        logger.info("batch id is :" + batchId + ",as of date is :" + asofdate);
        if (1 != batchDefineService.updateAsofdate(asofdate, batchId)) {
            response.setStatus(421);
            return Hret.error(421, "更新批次日期失败", JSONObject.NULL);
        }

        return Hret.success(200, "success", JSONObject.NULL);
    }

    private BatchDefineEntity parse(HttpServletRequest request) {
        String userId = JwtService.getConnectUser(request).get("UserId").toString();
        BatchDefineEntity batchDefineEntity = new BatchDefineEntity();
        String batchId = JoinCode.join(request.getParameter("domain_id"), request.getParameter("batch_id"));
        batchDefineEntity.setBatchId(batchId);
        batchDefineEntity.setCodeNumber(request.getParameter("batch_id"));
        batchDefineEntity.setRetMsg("");
        batchDefineEntity.setCompleteDate(request.getParameter("complete_date"));
        batchDefineEntity.setDomainId(request.getParameter("domain_id"));
        batchDefineEntity.setBatchDesc(request.getParameter("batch_desc"));
        batchDefineEntity.setBatchStatus(request.getParameter("batch_status"));
        batchDefineEntity.setAsOfDate(request.getParameter("as_of_date"));
        return batchDefineEntity;
    }
}
