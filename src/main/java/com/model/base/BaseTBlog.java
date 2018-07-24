package com.model.base;

import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseTBlog<M extends BaseTBlog<M>> extends BaseModel<M> implements IBean {
	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	public java.lang.Integer getId() {
		return getInt("id");
	}
	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}
	public java.lang.String getName() {
		return getStr("name");
	}
	public M setDesc(java.lang.String desc) {
		set("desc", desc);
		return (M)this;
	}
	public java.lang.String getDesc() {
		return getStr("desc");
	}
}
