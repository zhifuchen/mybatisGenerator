package ${packageName}.mapper;

import com.as.mybatisplus.mapper.BaseMapper;
import com.as.mybatisplus.plugins.pagination.Pagination;
import ${packageName}.api.entity.${entityClassName};
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ${entityClassName}Mapper extends BaseMapper<${entityClassName}> {
    List<${entityClassName}> getPageByMap(Pagination page, Map<String, Object> map);
}