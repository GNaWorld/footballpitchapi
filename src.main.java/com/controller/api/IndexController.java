package com.controller.api;

import java.util.List;

import com.common.controller.BaseController;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.model.Blog;

@ControllerBind(controllerKey = "/index", viewPath = "/index")
public class IndexController extends BaseController{
	public void index() {
		renderText("gna secu");
	}
	public void sqlManager() {
		String sql = Blog.dao.getSql("blogList");
		renderText(sql);
	}
	public void doAdd() {
//		Blog blog = getModel(Blog.class, "b");
		Blog blog = new Blog();
		blog.set("name", "ert");
		blog.update();
		renderText("提交成功");
	}
	public void testSql() {
		String sql = "select * from t_blog order by id desc";
		List<Blog> blogs = Blog.dao.find(sql);
		renderJson(blogs);
//		Db.delete(sql);
	}
	public void test() {
		renderText("IndexController接口");
	}
}
