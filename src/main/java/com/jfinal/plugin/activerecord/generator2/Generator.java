/**
 * Copyright (c) 2011-2017, James Zhan ղ�� (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jfinal.plugin.activerecord.generator2;

import java.util.List;
import javax.sql.DataSource;
import com.jfinal.plugin.activerecord.dialect.Dialect;

/**
 * ������
 * 1������ʱ��ǿ�Ƹ��� Base model��MappingKit��DataDictionary�����鲻Ҫ�޸������ļ���������

���б仯��������һ�α��
 * 2������  Model ���Ḳ���Ѿ����ڵ��ļ���Model ͨ���ᱻ��Ϊ�޸ĺ�ά��
 * 3��MappingKit �ļ�Ĭ�ϻ������� Model �ļ���ͬʱ����
 * 4��DataDictionary �ļ�Ĭ�ϲ������ɡ�ֻ�������� setGenerateDataDictionary(true)�󣬻�����

�� Model�ļ���ͬʱ����
 * 5������ͨ���̳� BaseModelGenerator��ModelGenerator��MappingKitGenerator��

DataDictionaryGenerator
 *   �������Զ�����������Ȼ��ʹ�� Generator �� setter ����ָ���Զ���������������
 * 6������ģ����������ȫ��Ϊ protected �ɼ��ԣ������Զ��� Generator ���ɷ��ϡ�������
 */
public class Generator {
	
	protected Dialect dialect = null;
	protected MetaBuilder metaBuilder;
	protected BaseModelGenerator baseModelGenerator;
	protected ModelGenerator modelGenerator;
	protected MappingKitGenerator mappingKitGenerator;
	protected DataDictionaryGenerator dataDictionaryGenerator;
	protected boolean generateDataDictionary = false;
	
	/**
	 * ���� Generator������ BaseModel��Model��MappingKit �����ļ������� MappingKit ���

Ŀ¼������� Model��ͬ
	 * @param dataSource ����Դ
	 * @param baseModelPackageName base model ����
	 * @param baseModelOutputDir base mode ���Ŀ¼
	 * @param modelPackageName model ����
	 * @param modelOutputDir model ���Ŀ¼
	 */
	public Generator(DataSource dataSource, String baseModelPackageName, String 

baseModelOutputDir, String modelPackageName, String modelOutputDir) {
		this(dataSource, new BaseModelGenerator(baseModelPackageName, 

baseModelOutputDir), new ModelGenerator(modelPackageName, baseModelPackageName, 

modelOutputDir));
	}
	
	/**
	 * ���� Generator��ֻ���� baseModel
	 * @param dataSource ����Դ
	 * @param baseModelPackageName base model ����
	 * @param baseModelOutputDir base mode ���Ŀ¼
	 */
	public Generator(DataSource dataSource, String baseModelPackageName, String 

baseModelOutputDir) {
		this(dataSource, new BaseModelGenerator(baseModelPackageName, 

baseModelOutputDir));
	}
	
	public Generator(DataSource dataSource, BaseModelGenerator baseModelGenerator) {
		if (dataSource == null) {
			throw new IllegalArgumentException("dataSource can not be null.");
		}
		if (baseModelGenerator == null) {
			throw new IllegalArgumentException("baseModelGenerator can not be null.");
		}
		
		this.metaBuilder = new MetaBuilder(dataSource);
		this.baseModelGenerator = baseModelGenerator;
		this.modelGenerator = null;
		this.mappingKitGenerator = null;
		this.dataDictionaryGenerator = null;
	}
	
	/**
	 * ʹ��ָ�� BaseModelGenerator��ModelGenerator ���� Generator 
	 * ���� BaseModel��Model��MappingKit �����ļ������� MappingKit ���Ŀ¼������� 

Model��ͬ
	 */
	public Generator(DataSource dataSource, BaseModelGenerator baseModelGenerator, 

ModelGenerator modelGenerator) {
		if (dataSource == null) {
			throw new IllegalArgumentException("dataSource can not be null.");
		}
		if (baseModelGenerator == null) {
			throw new IllegalArgumentException("baseModelGenerator can not be null.");
		}
		if (modelGenerator == null) {
			throw new IllegalArgumentException("modelGenerator can not be null.");
		}
		
		this.metaBuilder = new MetaBuilder(dataSource);
		this.baseModelGenerator = baseModelGenerator;
		this.modelGenerator = modelGenerator;
		this.mappingKitGenerator = new MappingKitGenerator

(modelGenerator.modelPackageName, modelGenerator.modelOutputDir);
		this.dataDictionaryGenerator = new DataDictionaryGenerator(dataSource, 

modelGenerator.modelOutputDir);
	}
	
	/**
	 * ���� MetaBuilder��������չ�Զ��� MetaBuilder
	 */
	public void setMetaBuilder(MetaBuilder metaBuilder) {
		if (metaBuilder != null) {
			this.metaBuilder = metaBuilder;
		}
	}
	
	public void setTypeMapping(TypeMapping typeMapping) {
		this.metaBuilder.setTypeMapping(typeMapping);
	}
	
	/**
	 * ���� MappingKitGenerator��������չ�Զ��� MappingKitGenerator
	 */
	public void setMappingKitGenerator(MappingKitGenerator mappingKitGenerator) {
		if (mappingKitGenerator != null) {
			this.mappingKitGenerator = mappingKitGenerator;
		}
	}
	
	/**
	 * ���� DataDictionaryGenerator��������չ�Զ��� DataDictionaryGenerator
	 */
	public void setDataDictionaryGenerator(DataDictionaryGenerator 

dataDictionaryGenerator) {
		if (dataDictionaryGenerator != null) {
			this.dataDictionaryGenerator = dataDictionaryGenerator;
		}
	}
	
	/**
	 * �������ݿⷽ�ԣ�Ĭ��Ϊ MysqlDialect
	 */
	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}
	
	/**
	 * ������������ BaseModel ��ģ���ļ���ģ�����潫�� class path �� jar ����Ѱ��ģ����

��
	 * 
	 * Ĭ��ģ��Ϊ��"/com/jfinal/plugin/activerecord/generator/base_model_template.jf"
	 */
	public void setBaseModelTemplate(String baseModelTemplate) {
		baseModelGenerator.setTemplate(baseModelTemplate);
	}
	
	/**
	 * ���� BaseModel �Ƿ�������ʽ setter ����
	 */
	public void setGenerateChainSetter(boolean generateChainSetter) {
		baseModelGenerator.setGenerateChainSetter(generateChainSetter);
	}
	
	/**
	 * ������Ҫ���Ƴ��ı���ǰ׺������������ modelName ��  baseModelName
	 * �������  "osc_account"���Ƴ�ǰ׺ "osc_" ���Ϊ "account"
	 */
	public void setRemovedTableNamePrefixes(String... removedTableNamePrefixes) {
		metaBuilder.setRemovedTableNamePrefixes(removedTableNamePrefixes);
	}
	
	/**
	 * ��Ӳ���Ҫ��������ݱ�
	 */
	public void addExcludedTable(String... excludedTables) {
		metaBuilder.addExcludedTable(excludedTables);
	}
	
	/**
	 * ������������ Model ��ģ���ļ���ģ�����潫�� class path �� jar ����Ѱ��ģ���ļ�
	 * 
	 * Ĭ��ģ��Ϊ��"/com/jfinal/plugin/activerecord/generator/model_template.jf"
	 */
	public void setModelTemplate(String modelTemplate) {
		if (modelGenerator != null) {
			modelGenerator.setTemplate(modelTemplate);
		}
	}
	
	/**
	 * �����Ƿ��� Model ������ dao ����Ĭ������
	 */
	public void setGenerateDaoInModel(boolean generateDaoInModel) {
		if (modelGenerator != null) {
			modelGenerator.setGenerateDaoInModel(generateDaoInModel);
		}
	}
	
	/**
	 * �����Ƿ����������ֵ� Dictionary �ļ���Ĭ�ϲ�����
	 */
	public void setGenerateDataDictionary(boolean generateDataDictionary) {
		this.generateDataDictionary = generateDataDictionary;
	}
	
	/**
	 * ������������ MappingKit ��ģ���ļ���ģ�����潫�� class path �� jar ����Ѱ��ģ����

��
	 * 
	 * Ĭ��ģ��Ϊ��"/com/jfinal/plugin/activerecord/generator/mapping_kit_template.jf"
	 */
	public void setMappingKitTemplate(String mappingKitTemplate) {
		if (this.mappingKitGenerator != null) {
			this.mappingKitGenerator.setTemplate(mappingKitTemplate);
		}
	}
	
	/**
	 * ���� MappingKit �ļ����Ŀ¼��Ĭ���� modelOutputDir ��ͬ��
	 * �����ô˱�����ͬʱ��Ҫ���� mappingKitPackageName
	 */
	public void setMappingKitOutputDir(String mappingKitOutputDir) {
		if (this.mappingKitGenerator != null) {
			this.mappingKitGenerator.setMappingKitOutputDir

(mappingKitOutputDir);
		}
	}
	
	/**
	 * ���� MappingKit �ļ�������Ĭ���� modelPackageName ��ͬ��
	 * �����ô˱��ͬʱ��Ҫ���� mappingKitOutputDir
	 */
	public void setMappingKitPackageName(String mappingKitPackageName) {
		if (this.mappingKitGenerator != null) {
			this.mappingKitGenerator.setMappingKitPackageName

(mappingKitPackageName);
		}
	}
	
	/**
	 * ���� MappingKit ������Ĭ��ֵΪ: "_MappingKit"
	 */
	public void setMappingKitClassName(String mappingKitClassName) {
		if (this.mappingKitGenerator != null) {
			this.mappingKitGenerator.setMappingKitClassName

(mappingKitClassName);
		}
	}
	
	/**
	 * ���������ֵ� DataDictionary �ļ����Ŀ¼��Ĭ���� modelOutputDir ��ͬ
	 */
	public void setDataDictionaryOutputDir(String dataDictionaryOutputDir) {
		if (this.dataDictionaryGenerator != null) {
			this.dataDictionaryGenerator.setDataDictionaryOutputDir

(dataDictionaryOutputDir);
		}
	}
	
	/**
	 * ���������ֵ� DataDictionary �ļ����Ŀ¼��Ĭ��ֵΪ "_DataDictionary.txt"
	 */
	public void setDataDictionaryFileName(String dataDictionaryFileName) {
		if (dataDictionaryGenerator != null) {
			dataDictionaryGenerator.setDataDictionaryFileName

(dataDictionaryFileName);
		}
	}
	
	public void generate() {
		if (dialect != null) {
			metaBuilder.setDialect(dialect);
		}
		
		long start = System.currentTimeMillis();
		List<TableMeta> tableMetas = metaBuilder.build();
		if (tableMetas.size() == 0) {
			System.out.println("TableMeta ����Ϊ 0���������κ��ļ�");
			return ;
		}
		
		baseModelGenerator.generate(tableMetas);
		
		if (modelGenerator != null) {
			modelGenerator.generate(tableMetas);
		}
		
		if (mappingKitGenerator != null) {
			mappingKitGenerator.generate(tableMetas);
		}
		
		if (dataDictionaryGenerator != null && generateDataDictionary) {
			dataDictionaryGenerator.generate(tableMetas);
		}
		
		long usedTime = (System.currentTimeMillis() - start) / 1000;
		System.out.println("Generate complete in " + usedTime + " seconds.");
	}
}