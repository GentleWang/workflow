package jd.jr.workFlow.dao.impl;

import jd.jr.workFlow.dao.ProcessInstancesEntityMapper;
import jd.jr.workFlow.model.ProcessInstancesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Autor wangshuo7
 * @Date 2016/7/21 9:30
 */
@Component
public class ProcessInstancesEntityDao {

    @Autowired
    private ProcessInstancesEntityMapper processInstancesEntityMapper;

    public List<ProcessInstancesEntity> selectInstanceByBiz(String systemSourceID,String bizType,String bizID){
        ProcessInstancesEntity instanceParam = new ProcessInstancesEntity();
        instanceParam.setSystemSourceId(systemSourceID);
        instanceParam.setBizType(bizType);
        instanceParam.setBizID(bizID);
        List<ProcessInstancesEntity> instances = processInstancesEntityMapper.selectByParams(instanceParam);
        return instances;
    }

    public List<ProcessInstancesEntity> selectBizByInstance(String systemSourceID,String bizType,String instanceID){
        ProcessInstancesEntity instanceParam = new ProcessInstancesEntity();
        instanceParam.setSystemSourceId(systemSourceID);
        instanceParam.setBizType(bizType);
        instanceParam.setProcessIntanceId(instanceID);
        List<ProcessInstancesEntity> instances = processInstancesEntityMapper.selectByParams(instanceParam);
        return instances;
    }


}
