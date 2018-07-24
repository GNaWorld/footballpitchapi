package com.model.base;

import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSysSql<M extends BaseSysSql<M>> extends BaseModel<M> implements IBean {
	public M setId(java.lang.Long id) {
		set("id", id);
		return (M)this;
	}
	public java.lang.Long getId() {
		return getLong("id");
	}
	public M setSqlId(java.lang.String sqlId) {
		set("sql_id", sqlId);
		return (M)this;
	}
	public java.lang.String getSqlId() {
		return getStr("sql_id");
	}
	public M setSqlDesc(java.lang.String sqlDesc) {
		set("sql_desc", sqlDesc);
		return (M)this;
	}
	public java.lang.String getSqlDesc() {
		return getStr("sql_desc");
	}
	public M setSqlType(java.lang.String sqlType) {
		set("sql_type", sqlType);
		return (M)this;
	}
	public java.lang.String getSqlType() {
		return getStr("sql_type");
	}
	public M setSqlText(java.lang.String sqlText) {
		set("sql_text", sqlText);
		return (M)this;
	}
	public java.lang.String getSqlText() {
		return getStr("sql_text");
	}
	public M setSqlSta(java.lang.Integer sqlSta) {
		set("sql_sta", sqlSta);
		return (M)this;
	}
	public java.lang.Integer getSqlSta() {
		return getInt("sql_sta");
	}
}
