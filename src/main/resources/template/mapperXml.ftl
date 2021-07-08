<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.mapper.${entityClassName}Mapper">
  <#assign cols = "">
  <#assign cols2 = "">
  <#assign enter = "\r\n    ">
  <#assign comma = "`, ">
  <#assign tablePrefix = "${tableName?substring(0,1)}">
  <#assign l = 0>
  <#list columns as column>
    <#assign cols = cols + "`" + column + comma>
    <#assign cols2 = cols2 + tablePrefix+ ".`" + column + comma>
    <#assign l = l + column?length>
    <#if (l > 60)>
      <#assign cols = cols + enter>
      <#assign cols2 = cols2 + enter>
      <#assign l = 0>
    </#if>
  </#list>
<#--  可能以回车结尾,先移除回车-->
  <#if cols?ends_with(enter)>
    <#assign cols = cols?substring(0,cols?length-enter?length)>
  </#if>
  <#if cols2?ends_with(enter)>
    <#assign cols2 = cols2?substring(0,cols2?length-enter?length)>
  </#if>
<#--  移除结尾的逗号+空格-->
  <sql id="Base_Column_List">
    ${cols?substring(0,cols?length-2)}
  </sql>
  <sql id="Base_Column_List_${tablePrefix}">
    ${cols2?substring(0,cols2?length-2)}
  </sql>
  <select id="getPageByMap" resultType="${packageName}.api.entity.${entityClassName}">
    select <include refid="Base_Column_List" /> from ${tableName}
    <where>
      <if test="create_user != null">
        create_user = ${r"#{create_user}"}
      </if>
    </where>
  </select>
</mapper>