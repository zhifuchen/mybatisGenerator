<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.mapper.${entityClassName}Mapper">
  <sql id="Base_Column_List">
        ${columns}
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