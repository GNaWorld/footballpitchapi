package com.model;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class Blog extends Model<Blog>{
//	private static final long serialVersionUID = 1L;
	
	public static Blog dao = new Blog().dao();
}
