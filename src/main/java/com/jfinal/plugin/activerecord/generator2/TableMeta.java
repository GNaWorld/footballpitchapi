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

import java.util.ArrayList;
import java.util.List;

/**
 * TableMeta
 */
public class TableMeta {
	
	public String name;					// ����
	public String remarks;				// ��ע
	public String primaryKey;			// ���������������Զ��ŷָ�
	public List<ColumnMeta> columnMetas = new ArrayList<ColumnMeta>();	// �ֶ� meta
	
	// ---------
	
	public String baseModelName;		// ���ɵ� base model ��
	public String baseModelContent;		// ���ɵ� base model ����
	
	public String modelName;			// ���ɵ� model ��
	public String modelContent;			// ���ɵ� model ����
	
	// ---------
	
	public int colNameMaxLen = "Field".length();			// �ֶ�������ȣ����ڸ��������ֵ��ļ���ʽ
	public int colTypeMaxLen = "Type".length();				// �ֶ���������ȣ����ڸ��������ֵ��ļ���ʽ
	public int colDefaultValueMaxLen = "Default".length();	// �ֶ�Ĭ��ֵ����ȣ����ڸ��������ֵ��ļ���ʽ
}




