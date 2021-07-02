package mybatisGenerator.util;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by chenzhifu on 2018-10-30 14:27:06
 */
public class XmlUtil {
    private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);
    public static Map<String,Object> parserXml(InputStream inputStream) {
        SAXReader saxReader = new SAXReader();
        Map<String,Object> pageData = new HashMap<>();
        try {
            Document document = saxReader.read(inputStream);
            List<Node> tableList = document.selectNodes("//table");
            for (Node node : tableList) {
                pageData.put(((Element) node).attribute("domainObjectName").getValue(),((Element) node).attribute("tableName").getValue());
            }
        } catch (DocumentException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return pageData;
    }
    public static String[] parserXml2(InputStream inputStream) {
        SAXReader saxReader = new SAXReader();
        String[] strings = new String[3];
        try {
            Document document = saxReader.read(inputStream);
            List<Node> tableList = document.selectNodes("//jdbcConnection");
            for (Node node : tableList) {
                Attribute connectionURL = ((Element) node).attribute("connectionURL");
                Attribute userId = ((Element) node).attribute("userId");
                Attribute password = ((Element) node).attribute("password");
                strings[0] = connectionURL.getValue();
                strings[1] = userId.getValue();
                strings[2] = password.getValue();
            }
        } catch (DocumentException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return strings;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String projectRootPath = "E:/zhifu/ideaProject/mybatisGenerator";
        String[] strings = XmlUtil.parserXml2(new FileInputStream(projectRootPath + "/src/main/resources/generatorConfig.xml"));
        System.out.println(strings);

    }

}
