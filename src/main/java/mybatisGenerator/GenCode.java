package mybatisGenerator;

import com.google.common.base.CaseFormat;
import freemarker.template.Configuration;
import mybatisGenerator.util.FreeMarkerUtils;
import mybatisGenerator.util.MysqlUtil;
import mybatisGenerator.util.XmlUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenCode {
    public static void main(String[] args) throws Exception {
        String packageName = "com.giantweather.farmfriend";
        String projectRootPath = "E:/zhifu/ideaProject/mybatisGenerator";
        String srcPath = "/src/main/java/";
        Configuration webConfiguration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        webConfiguration.setDirectoryForTemplateLoading(new File(projectRootPath + "/src/main/resources/template"));
        String generatorConfigFile = "/src/main/resources/generatorConfig.xml";
        Map<String, Object> entityMap = XmlUtil.parserXml(new FileInputStream(projectRootPath + generatorConfigFile));
        String[] dbConn = XmlUtil.parserXml2(new FileInputStream(projectRootPath + generatorConfigFile));
        for (Map.Entry<String, Object> entry : entityMap.entrySet()) {
            String entityClassName = entry.getKey();
            String entityVarName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, entityClassName);
            Map<String, Object> map = new HashMap<>();
            map.put("entityClassName", entityClassName);
            map.put("entityVarName", entityVarName);
            map.put("packageName", packageName);
            String tableName = String.valueOf(entry.getValue());
            map.put("tableName", tableName);
            List<String> columns = MysqlUtil.getColumns(dbConn[0], dbConn[1], dbConn[2], tableName);
            map.put("columns", columns);
            //生成controller
            String controllerPath = projectRootPath + srcPath + packageName.replace(".", "/") + "/controller";
            FileUtils.forceMkdir(new File(controllerPath));
            String controllerFilePath = controllerPath + "/" + entityClassName + "Controller.java";
            FreeMarkerUtils.generateFileByFile("controller.ftl",
                    controllerFilePath, webConfiguration, map, true);

            //生成service
            String servicePath = projectRootPath + srcPath + packageName.replace(".", "/") + "/api/service";
            FileUtils.forceMkdir(new File(servicePath));
            String serviceFilePath = servicePath + "/" + entityClassName + "Service.java";
            FreeMarkerUtils.generateFileByFile("service.ftl",
                    serviceFilePath, webConfiguration, map, true);
            //生成serviceImpl
            String serviceImplPath = projectRootPath + srcPath + packageName.replace(".", "/") + "/service";
            FileUtils.forceMkdir(new File(serviceImplPath));
            String serviceImplFilePath = serviceImplPath + "/" + entityClassName + "ServiceImpl.java";
            FreeMarkerUtils.generateFileByFile("serviceImpl.ftl",
                    serviceImplFilePath, webConfiguration, map, true);
            //生成mapper类
            String mapperClassPath = projectRootPath + srcPath + packageName.replace(".", "/") + "/mapper";
            FileUtils.forceMkdir(new File(mapperClassPath));
            String mapperClassFilePath = mapperClassPath + "/" + entityClassName + "Mapper.java";
            FreeMarkerUtils.generateFileByFile("mapperClass.ftl",
                    mapperClassFilePath, webConfiguration, map, true);

            //生成mapper xml
            String mapperXmlPath = projectRootPath + "/src/main/resources/mapper";
            FileUtils.forceMkdir(new File(mapperXmlPath));
            String mapperXmlFilePath = mapperXmlPath + "/" + entityClassName + "Mapper.xml";
            FreeMarkerUtils.generateFileByFile("mapperXml.ftl",
                    mapperXmlFilePath, webConfiguration, map, true);

            //生成entity
            String entityPath = projectRootPath + srcPath + packageName.replace(".", "/") + "/api/entity";
            FileUtils.forceMkdir(new File(entityPath));
            Class clz=Class.forName(packageName + ".api.entity." + entityClassName);
            Field[] fields = clz.getDeclaredFields();
            List<Map<String, Object>> fieldList = new ArrayList<>();
            List<String> have = new ArrayList<>();
            have.add("id");
            have.add("create_time");
            have.add("update_time");
            have.add("create_user");
            have.add("update_user");
            for (Field field : fields) {
                int modifiers = field.getModifiers();
                String name = field.getName();
                if (Modifier.isPrivate(modifiers) && !have.contains(name)) {
                    HashMap<String, Object> fieldMap = new HashMap<>();
                    String type = field.getType().getSimpleName();
                    fieldMap.put("type", type);
                    fieldMap.put("name", name);
                    fieldList.add(fieldMap);
                }
            }
            map.put("fields", fieldList);
            String entityFilePath = entityPath + "/" + entityClassName + ".java";
            FreeMarkerUtils.generateFileByFile("entity.ftl",entityFilePath, webConfiguration, map, true);

        }
    }
}
