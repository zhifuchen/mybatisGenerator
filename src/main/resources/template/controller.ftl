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
public class ${entityClassName}Controller {
    @DubboReference
    private ${entityClassName}Service ${entityVarName}Service;

    @ApiOperation(value = "保存")
    @PostMapping("/save")
    public RestResult save(@RequestBody ${entityClassName} ${entityVarName}) {
        // TODO: 2021/6/25 025 createuser
        ${entityVarName}Service.save(${entityVarName});
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
    @GetMapping("/getPageList")
    public RestResult getPageList(HttpServletRequest request, @RequestParam(defaultValue = "1") int current,
    @RequestParam(defaultValue = "10") int size) {
        String token = request.getHeader("TOKEN");
        Integer userId = 1;
        // TODO: 2021/6/18 018
        Page<${entityClassName}> page = new Page<>(current,size);
        HashMap<String, Object> map = new HashMap<>();
        map.put("create_user", userId);
        Page<${entityClassName}> pageList = ${entityVarName}Service.selectPageList(page, map);
        return RestResult.ok("成功", pageList);
    }
    @GetMapping("/getList")
    public RestResult getList(HttpServletRequest request) {
        List<${entityClassName}> list = ${entityVarName}Service.selectList(null);
        return RestResult.ok("成功", list);
    }
}
