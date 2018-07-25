import com.common.plugins.xmlsql.SqlDbPlugin;
import com.common.plugins.xmlsql.SqlXmlPlugin;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.route.AutoBindRoutes;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.model._MappingKit;

public class MainConfig extends JFinalConfig{

	/**
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 * <p>
	 * 使用本方法启动过第一次以后，会在开发工具的 debug、run config 中自动生成 一条启动配置，可对该自动生成的配置再添加额外的配置项，例如 VM
	 * argument 可配置为： -XX:PermSize=64M -XX:MaxPermSize=256M
	 */
	
	/**
	 * 配置常量
	 */
	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("a_little_config.txt");
		//是否是开发模式
		me.setDevMode(PropKit.getBoolean("devMode", false));
	}

	/**
	 * 配置路由
	 */
	@Override
	public void configRoute(Routes me) {
		// TODO Auto-generated method stub
//		me.add("/", IndexController.class);
//		me.add("/", AuthController.class);
		AutoBindRoutes abr = new AutoBindRoutes();
		abr.autoScan(false);
		me.add(abr);
	}

	/**
	 * 配置模板
	 */
	@Override
	public void configEngine(Engine me) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 配置插件
	 */
	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub
//		Prop p = PropKit.use("a_little_config.txt");
		//
//		C3p0Plugin c3p0Plugin = new C3p0Plugin(p.get("jdbcUrl"), p.get("user"), p.get("password"));
		 
		//阿里巴巴数据库连接池——为了使用model必配的东西
		DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"),
				PropKit.get("password").trim());
		me.add(druidPlugin);
		
		//视频
		//配置ActiveRecord插件,与数据库交互作用
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
//		System.out.println("11111 ---- " + PathKit.getWebRootPath());
		_MappingKit.mapping(arp);
		me.add(arp);
		
		SqlXmlPlugin xmlsql = new SqlXmlPlugin();
		me.add(xmlsql);

		SqlDbPlugin dbsql = new SqlDbPlugin();
		me.add(dbsql);
		
//		arp.setBaseSqlTemplatePath(PathKit.getWebRootPath() + "/WEB-INF");
//		arp.addSqlTemplate("/sql/demo.sql");
//		arp.addMapping("t_blog", Blog.class);//数据库映射，需要在加入PluginList之前完成配置，必须先做好
//		me.add(arp);
		
		// 配置ActiveRecord插件
//				ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
//				// 所有映射在 MappingKit 中自动化搞定
////				_MappingKit.mapping(arp);
//				arp.addMapping(tableName, modelClass)
//				me.add(arp);
		
		// 配置ActiveRecord插件——数据库连接池，并且让他启动
//		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
//		//设置sql文件的起始文件夹
////		arp.setBaseSqlTemplatePath(PathKit.getWebRootPath() + "/WEB-INF");
//		/*
//		 * 设置了 sql 文件存放的基础路径，注意上例代 码将基础路径设置为了 classpath 的根，
//		 * 可以将 sql 文件放在 maven 项目下的 resources 之下， 编译器会自动将其编译至 classpath 之下，
//		 * 该路径可按喜好自由设置
//		 */
//		arp.setBaseSqlTemplatePath(PathKit.getWebRootPath());
////		arp.addSqlTemplate("/demo.sql");
//		arp.addMapping("t_blog", Blog.class);
//		// 所有映射在 MappingKit 中自动化搞定 
//		me.add(arp);		
	}

	/**
	 * 配置全局拦截器
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 配置处理器
	 */
	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 框架启动后
	 */
	@Override
	public void afterJFinalStart() {
//		System.out.println("afterJFinalStart...");
	}
	
	/**
	 * 框架停止后
	 */
	@Override
	public void beforeJFinalStop() {
		System.out.println("beforeJFinalStop...");
	}
	
	public static DruidPlugin createDruidPlugin() {
		return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
	}

	public static void main(String[] args) {
		/**
		 * 特别注意：Eclipse 之下建议的启动方式
		 */
//		JFinal.start("src/main/webapp", 8080, "/", 5);
		//链接数据库，最新版的eclipse
		JFinal.start("src/main/webapp", 9999, "/");
		/**
		 * 特别注意：IDEA 之下建议的启动方式，仅比 eclipse 之下少了最后一个参数
		 */
//		JFinal.start("src/main/webapp", 8080, "/");
	}
}
