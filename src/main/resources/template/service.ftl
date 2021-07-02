package ${packageName}.api.service;

import com.as.mybatisplus.plugins.Page;
import ${packageName}.api.entity.${entityClassName};

import java.util.List;
import java.util.Map;

public interface ${entityClassName}Service {
    void save(${entityClassName} ${entityVarName});

    void delete(Long id);

    void update(${entityClassName} ${entityVarName});

    ${entityClassName} getById(Long id);

    List<${entityClassName}> selectList(Map<String, Object> param);

    Page<${entityClassName}> selectPageList(Page<${entityClassName}> page, Map<String, Object> param);

}
