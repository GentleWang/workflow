package jd.jr.workFlow.dao;

import jd.jr.workFlow.model.ProcessDefinitionEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ProcessDefinitionEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProcessDefinitionEntity record);

    ProcessDefinitionEntity selectByPrimaryKey(Integer id);

    List<ProcessDefinitionEntity> selectAll();

    int updateByPrimaryKey(ProcessDefinitionEntity record);

    List<ProcessDefinitionEntity> selectByParams(ProcessDefinitionEntity processDefinitionEntity);
}