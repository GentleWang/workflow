package jd.jr.workFlow.dao;

import jd.jr.workFlow.model.ProcessInstancesEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ProcessInstancesEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProcessInstancesEntity record);

    ProcessInstancesEntity selectByPrimaryKey(String id);

    List<ProcessInstancesEntity> selectAll();

    int updateByPrimaryKey(ProcessInstancesEntity record);

    List<ProcessInstancesEntity> selectByParams(ProcessInstancesEntity params);
}