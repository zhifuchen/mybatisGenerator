package ${packageName}.api.entity;

import com.as.mybatisplus.annotations.TableField;
import com.as.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
@Data
@TableName("${tableName}")
public class ${entityClassName} extends BaseEntity {
<#list fields as field>
    private ${field.type} ${field.name};
</#list>
}