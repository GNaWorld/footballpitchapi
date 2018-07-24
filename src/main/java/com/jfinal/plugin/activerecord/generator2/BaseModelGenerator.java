/**
 * Copyright (c) 2011-2017, James Zhan ղ�� (jfinal@126.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jfinal.plugin.activerecord.generator2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.JavaKeyword;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSourceFactory;

/**
 * Base model ������
 */
public class BaseModelGenerator {

    protected String template = "/com/jfinal/plugin/activerecord/generator2/base_model_template.jf";

    protected String baseModelPackageName;
    protected String baseModelOutputDir;
    protected boolean generateChainSetter = false;

    protected JavaKeyword javaKeyword = JavaKeyword.me;

    /**
     * ��� Model �����ֿ����Զ�ת�����͵� getter ���������������ȷ�����ͷ���ֵ�� getter ����
     * �����Զ�����ת���ı����ԣ����� getInt(String)��getStr(String)
     * ��������ʹ�÷��ͷ���ֵ������ get(String)
     * ע�⣺jfinal 3.2 �����ϰ汾 Model �е����� getter �����ž�������ת������
     */
    @SuppressWarnings("serial")
    protected Map<String, String> getterTypeMap = new HashMap<String, String>() {{
        put("java.lang.String", "getStr");
        put("java.lang.Integer", "getInt");
        put("java.lang.Long", "getLong");
        put("java.lang.Double", "getDouble");
        put("java.lang.Float", "getFloat");
        put("java.lang.Short", "getShort");
        put("java.lang.Byte", "getByte");
    }};

    public BaseModelGenerator(String baseModelPackageName, String baseModelOutputDir) {
        if (StrKit.isBlank(baseModelPackageName)) {
            throw new IllegalArgumentException("baseModelPackageName can not be blank.");
        }
        if (baseModelPackageName.contains("/") || baseModelPackageName.contains("\\")) {
            throw new IllegalArgumentException("baseModelPackageName error : " + baseModelPackageName);
        }
        if (StrKit.isBlank(baseModelOutputDir)) {
            throw new IllegalArgumentException("baseModelOutputDir can not be blank.");
        }

        this.baseModelPackageName = baseModelPackageName;
        this.baseModelOutputDir = baseModelOutputDir;
    }

    /**
     * ʹ���Զ���ģ������ base model
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    public void setGenerateChainSetter(boolean generateChainSetter) {
        this.generateChainSetter = generateChainSetter;
    }

    public void generate(List<TableMeta> tableMetas) {
        System.out.println("Generate base model ...");
        System.out.println("Base Model Output Dir: " + baseModelOutputDir);

        Engine engine = Engine.create("forBaseModel");
        engine.setSourceFactory(new ClassPathSourceFactory());
        engine.addSharedMethod(new StrKit());
        engine.addSharedObject("getterTypeMap", getterTypeMap);
        engine.addSharedObject("javaKeyword", javaKeyword);

        for (TableMeta tableMeta : tableMetas) {
            genBaseModelContent(tableMeta);
        }
        writeToFile(tableMetas);
    }

    protected void genBaseModelContent(TableMeta tableMeta) {
        Kv data = Kv.by("baseModelPackageName", baseModelPackageName);
        data.set("generateChainSetter", generateChainSetter);
        data.set("tableMeta", tableMeta);

        Engine engine = Engine.use("forBaseModel");
        tableMeta.baseModelContent = engine.getTemplate(template).renderToString(data);
    }

    protected void writeToFile(List<TableMeta> tableMetas) {
        try {
            for (TableMeta tableMeta : tableMetas) {
                writeToFile(tableMeta);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * base model ����д��
     */
    protected void writeToFile(TableMeta tableMeta) throws IOException {
        File dir = new File(baseModelOutputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String target = baseModelOutputDir + File.separator + tableMeta.baseModelName + ".java";
        FileWriter fw = new FileWriter(target);
        try {
            fw.write(tableMeta.baseModelContent);
        } finally {
            fw.close();
        }
    }
}






