package mybatisGenerator;

import com.google.common.base.CaseFormat;
import freemarker.template.Configuration;
import mybatisGenerator.util.FreeMarkerUtils;
import mybatisGenerator.util.MysqlUtil;
import mybatisGenerator.util.XmlUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenCode {
    public static void main(String[] args) throws Exception {
        String packageName = "com.giantweather.farmfriend";
        String projectRootPath = "E:/zhifu/ideaProject/mybatisGenerator";
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
            map.put("columns", StringUtils.join(columns.toArray(),", "));
            //生成controller
            String controllerPath = projectRootPath + "/src/main/java/" + packageName.replace(".", "/") + "/controller";
            FileUtils.forceMkdir(new File(controllerPath));
            String controllerFilePath = controllerPath + "/" + entityClassName + "Controller.java";
            FreeMarkerUtils.generateFileByFile("controller.ftl",
                    controllerFilePath, webConfiguration, map, true);

            //生成service
            String servicePath = projectRootPath + "/src/main/java/" + packageName.replace(".", "/") + "/api/service";
            FileUtils.forceMkdir(new File(servicePath));
            String serviceFilePath = servicePath + "/" + entityClassName + "Service.java";
            FreeMarkerUtils.generateFileByFile("service.ftl",
                    serviceFilePath, webConfiguration, map, true);
            //生成serviceImpl
            String serviceImplPath = projectRootPath + "/src/main/java/" + packageName.replace(".", "/") + "/service";
            FileUtils.forceMkdir(new File(serviceImplPath));
            String serviceImplFilePath = serviceImplPath + "/" + entityClassName + "ServiceImpl.java";
            FreeMarkerUtils.generateFileByFile("serviceImpl.ftl",
                    serviceImplFilePath, webConfiguration, map, true);
            //生成mapper类
            String mapperClassPath = projectRootPath + "/src/main/java/" + packageName.replace(".", "/") + "/mapper";
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
        }
    }
}
