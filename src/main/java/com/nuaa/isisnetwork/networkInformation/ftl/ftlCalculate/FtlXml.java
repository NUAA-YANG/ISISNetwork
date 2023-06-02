package com.nuaa.isisnetwork.networkInformation.ftl.ftlCalculate;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author YZX
 * @Create 2023-05-17 17:00
 * @Java-version jdk1.8
 */
@Service
@Component
public class FtlXml {


    /**
     * @description  将传入的Map键值对转化为xml文件
     * @date 2023/6/1 20:30
     * @params [dataMap：传入的键值对集合,savePath：生成的xml存放路径]
     * @returns void
     */
    public void ftlToXml(Map<String, Object> dataMap,String savePath) throws IOException, TemplateException {
        //设置版本
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
        //加载ftl模板所在的父级目录
        configuration.setDirectoryForTemplateLoading(new File("src/main/java/com/nuaa/isisnetwork/networkInformation/ftl/ftlExample"));
        configuration.setDefaultEncoding("utf-8");
        //选择相关ftl配置文件
        Template template = configuration.getTemplate("RouterExample.ftl", "utf-8");;
        String lxdName = (String) dataMap.get("lxdName");
        String manufacture = (String) dataMap.get("manufacture");
        //创建一个位置用于存放生成的文件，指定生成xml文件
        Writer writer = new FileWriter(new File(savePath+"/"+lxdName+"_"+manufacture+"_"+".xml"));
        template.process(dataMap,writer);
    }

}
