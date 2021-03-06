package ${packageName}.service;

import com.as.mybatisplus.plugins.Page;
import ${packageName}.api.entity.${entityClassName};
import ${packageName}.api.service.${entityClassName}Service;
import ${packageName}.mapper.${entityClassName}Mapper;
import com.giantweather.common.util.JodaTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@DubboService(protocol = "dubbo")
public class ${entityClassName}ServiceImpl implements ${entityClassName}Service {
    @Autowired
    private ${entityClassName}Mapper ${entityVarName}Mapper;

    @Override
    public void save(${entityClassName} ${entityVarName}) {
        Date now = JodaTimeUtil.now().toDate();
        ${entityVarName}.setCreate_time(now);
        ${entityVarName}.setUpdate_time(now);
        ${entityVarName}Mapper.insert(${entityVarName});
    }

    @Override
    public void delete(long id) {
        ${entityVarName}Mapper.deleteById(id);
    }

    @Override
    public void update(${entityClassName} ${entityVarName}) {
        Date now = JodaTimeUtil.now().toDate();
        ${entityVarName}.setUpdate_time(now);
        ${entityVarName}Mapper.updateById(${entityVarName});
    }

    @Override
    public ${entityClassName} getById(long id) {
        return ${entityVarName}Mapper.selectById(id);
    }

    @Override
    public List<${entityClassName}> selectList(Map<String, Object> param) {
        return ${entityVarName}Mapper.selectByMap(param);
    }

    @Override
    public ${entityClassName} selectOne(${entityClassName} ${entityVarName}) {
        return ${entityVarName}Mapper.selectOne(${entityVarName});
    }

    @Override
    public Page<${entityClassName}> selectPageList(Page<${entityClassName}> page, Map<String, Object> param) {
        return page.setRecords(${entityVarName}Mapper.getPageByMap(page,param));
    }
}
