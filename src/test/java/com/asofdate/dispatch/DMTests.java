package com.asofdate.dispatch;

import com.asofdate.dispatch.dao.*;
import com.asofdate.dispatch.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DMTests {
    @Autowired
    public TaskDefineDao taskDefineDao;

    @Autowired
    public ArgumentDefineDao argumentDefineDao;

    @Autowired
    public BatchArgumentDao batchArgumentDao;

    @Autowired
    public BatchGroupDao batchGroupDao;

    @Autowired
    public GroupDefineDao groupDefineDao;

    @Autowired
    public BatchDefineDao batchDefineDao;

    @Autowired
    public GroupTaskDao groupTaskDao;

    @Autowired
    public TaskArgumentDao taskArgumentDao;


    @Test
    public void testTaskDefineDao(){
        List<TaskDefineEntity> list = taskDefineDao.findAll("mas");
        for (TaskDefineEntity m: list){
            System.out.print("code number is :" + m.getCodeNumber());
            System.out.print("code number is :" + m.getCreateUser());
            System.out.print("code number is :" + m.getCreateDate());
            System.out.print("code number is :" + m.getTaskId());
            System.out.print("code number is :" + m.getTaskDesc());
            System.out.print("code number is :" + m.getTaskType());
            System.out.print("code number is :" + m.getTaskTypeDesc());
            System.out.println("");
        }
    }

    @Test
    public void testArgumentDefineDao(){
        List<ArgumentDefineEntity> list = argumentDefineDao.findAll("mas");
        for (ArgumentDefineEntity m:list){
            System.out.println("argument id is :" + m.getArgId());
        }
    }


    @Test
    public void testBatchGroupDao(){
        List<BatchGroupEntity> list = batchGroupDao.findAll("mas");
        for (BatchGroupEntity m:list){
            System.out.println("Batch is :" + m.getBatchId()+", group id is :" + m.getGroupId());
        }
    }

    @Test
    public void testGroupDefineDao(){
        List<GroupDefineEntity> list = groupDefineDao.findAll("mas");
        for (GroupDefineEntity m:list){
            System.out.println("Group id is: "+m.getGroupId());
        }
    }

    @Test
    public void testGroupTaskDao(){
        List<GroupTaskEntity> list = groupTaskDao.findAll("mas");
        for (GroupTaskEntity m:list){
            System.out.println("group id is:" + m.getGroupId()+",task id is:" + m.getTaskId());
        }
    }

    @Test
    public void testTaskArgumentDao(){
        List<TaskArgumentEntity> list = taskArgumentDao.findAll("mas");
        for (TaskArgumentEntity m: list){
            System.out.println("task id is :" + m.getTaskId()+",argument id is:"+m.getArgId());
        }
    }

    @Test
    public void testBatchDefineDao(){
        List<BatchDefineEntity> list = batchDefineDao.findAll("mas");
        for (BatchDefineEntity m:list){
            System.out.println("batch id is:" + m.getBatchDesc());
        }
    }

}
