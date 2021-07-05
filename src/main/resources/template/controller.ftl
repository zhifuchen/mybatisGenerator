package ${packageName}.controller;

import com.as.mybatisplus.plugins.Page;
import ${packageName}.api.entity.${entityClassName};
import ${packageName}.api.service.${entityClassName}Service;
import ${packageName}.util.RestResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Api(tags = "")
@RestController
@RequestMapping("/${entityVarName}")
public class ${entityClassName}Controller extends BaseController{
    @DubboReference
    private ${entityClassName}Service ${entityVarName}Service;

    @ApiOperation(value = "新增")
    @PostMapping("/save")
    public RestResult save(@RequestBody ${entityClassName} ${entityVarName}) {
        ${entityVarName}.setCreate_user(getUserId());
        ${entityVarName}Service.save(${entityVarName});
        return RestResult.ok("成功", ${entityVarName});
    }

    @ApiOperation(value = "修改")
    @PostMapping("/update")
    public RestResult update(@RequestBody ${entityClassName} ${entityVarName}) {
        ${entityVarName}.setUpdate_user(getUserId());
        ${entityVarName}Service.update(${entityVarName});
        return RestResult.ok("成功", ${entityVarName});
    }

    @PostMapping("/delete")
    public RestResult delete(@RequestBody ${entityClassName} ${entityVarName}) {
        ${entityVarName}Service.delete(${entityVarName}.getId());
        return RestResult.ok("成功", null);
    }

    @GetMapping("/getById")
    public RestResult getById(@RequestParam long id) {
        ${entityClassName} ${entityVarName} = ${entityVarName}Service.getById(id);
        return RestResult.ok("成功", ${entityVarName});
    }
    @ApiOperation("获取当前用户的列表-分页")
    @GetMapping("/getPageList")
    public RestResult getPageList(HttpServletRequest request, @RequestParam(defaultValue = "1") int current,
    @RequestParam(defaultValue = "10") int size) {
        Page<${entityClassName}> page = new Page<>(current,size);
        HashMap<String, Object> map = new HashMap<>();
        map.put("create_user", getUserId());
        Page<${entityClassName}> pageList = ${entityVarName}Service.selectPageList(page, map);
        return RestResult.ok("成功", pageList);
    }
    @ApiOperation("获取当前用户的列表")
    @GetMapping("/getList")
    public RestResult getList(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("create_user", getUserId());
        List<${entityClassName}> list = ${entityVarName}Service.selectList(map);
        return RestResult.ok("成功", list);
    }
}
