package mybatisGenerator.util;

import freemarker.core.ParseException;
import freemarker.template.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

/**
 * FreeMarker工具类
 * 
 * FreeMarkerUtils
 * 
 */
@Slf4j
public class FreeMarkerUtils {

    /**
     * @param templateFilePath
     * @param destFilePath
     * @param configuration
     * @param model
     * @throws IOException
     * @throws TemplateException
     */
    public static void generateFileByFile(String templateFilePath, String destFilePath, Configuration configuration,
            Map<String, Object> model) throws IOException, TemplateException {
        generateFileByFile(templateFilePath, destFilePath, configuration, model, true, false);
    }

    /**
     * @param templateFilePath
     * @param destFilePath
     * @param configuration
     * @param model
     * @param override
     * @throws IOException
     * @throws TemplateException
     */
    public static void generateFileByFile(String templateFilePath, String destFilePath, Configuration configuration,
            Map<String, Object> model, boolean override) throws IOException, TemplateException {
        generateFileByFile(templateFilePath, destFilePath, configuration, model, override, false);
    }

    /**
     * @param templateFilePath
     * @param destFilePath
     * @param configuration
     * @param model
     * @param override
     * @param append
     * @throws ParseException
     * @throws MalformedTemplateNameException
     * @throws IOException
     * @throws TemplateException
     */
    public static void generateFileByFile(String templateFilePath, String destFilePath, Configuration configuration,
            Map<String, Object> model, boolean override, boolean append)
            throws MalformedTemplateNameException, ParseException, IOException, TemplateException {
        Template t = configuration.getTemplate(templateFilePath);
        File destFile = new File(destFilePath);
        if (override || append || !destFile.exists()) {
            File parent = destFile.getParentFile();
            if (null != parent) {
                parent.mkdirs();
            }
            try (FileOutputStream outputStream = new FileOutputStream(destFile, append);) {
                Writer out = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
                t.process(model, out);
                log.info(destFilePath + " saved!");
            }
        } else {
            log.error(destFilePath + " already exists!");
        }
    }

    /**
     * @param template
     * @param configuration
     * @return render result
     * @throws TemplateException
     * @throws IOException
     */
    public static String generateStringByFile(String template, Configuration configuration)
            throws IOException, TemplateException {
        Map<String, Object> model = Collections.emptyMap();
        return generateStringByFile(template, configuration, model);
    }

    /**
     * @param template
     * @param configuration
     * @param model
     * @return render result
     * @throws IOException
     * @throws TemplateException
     */
    public static String generateStringByFile(String template, Configuration configuration, Map<String, Object> model)
            throws IOException, TemplateException {
        StringWriter writer = new StringWriter();
        generateStringByFile(writer, template, configuration, model);
        return writer.toString();
    }

    /**
     * @param writer
     * @param template
     * @param configuration
     * @param model
     * @throws TemplateNotFoundException
     * @throws MalformedTemplateNameException
     * @throws ParseException
     * @throws IOException
     * @throws TemplateException
     */
    public static void generateStringByFile(Writer writer, String template, Configuration configuration,
            Map<String, Object> model)
            throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
        Template tpl = configuration.getTemplate(template);
        tpl.process(model, writer);
    }

    /**
     * @param templateContent
     * @param configuration
     * @param model
     * @return render result
     * @throws IOException
     * @throws TemplateException
     */
    public static String generateStringByString(String templateContent, Configuration configuration, Map<String, Object> model)
            throws IOException, TemplateException {
        Template tpl = new Template(null, templateContent, configuration);
        StringWriter writer = new StringWriter();
        tpl.process(model, writer);
        return writer.toString();
    }
}
