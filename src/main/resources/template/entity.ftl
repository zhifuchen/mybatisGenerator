package ${packageName}.api.entity;

import com.as.mybatisplus.annotations.TableField;
import com.as.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.giantweather.common.base.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
@TableName("${tableName}")
public class ${entityClassName} extends BaseEntity {
<#list fields as field>
    private ${field.type} ${field.name};
</#list>
}