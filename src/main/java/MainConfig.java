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
	 * ���д� main ��������������Ŀ����main�������Է����������Class�ඨ���У���һ��Ҫ���ڴ�
	 * <p>
	 * ʹ�ñ�������������һ���Ժ󣬻��ڿ������ߵ� debug��run config ���Զ����� һ���������ã��ɶԸ��Զ����ɵ���������Ӷ������������� VM
	 * argument ������Ϊ�� -XX:PermSize=64M -XX:MaxPermSize=256M
	 */
	
	/**
	 * ���ó���
	 */
	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub
		// ����������Ҫ���ã�������PropKit.get(...)��ȡֵ
		PropKit.use("a_little_config.txt");
		//�Ƿ��ǿ���ģʽ
		me.setDevMode(PropKit.getBoolean("devMode", false));
	}

	/**
	 * ����·��
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
	 * ����ģ��
	 */
	@Override
	public void configEngine(Engine me) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * ���ò��
	 */
	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub
//		Prop p = PropKit.use("a_little_config.txt");
		//
//		C3p0Plugin c3p0Plugin = new C3p0Plugin(p.get("jdbcUrl"), p.get("user"), p.get("password"));
		 
		//����Ͱ����ݿ����ӳء���Ϊ��ʹ��model����Ķ���
		DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"),
				PropKit.get("password").trim());
		me.add(druidPlugin);
		
		//��Ƶ
		//����ActiveRecord���,�����ݿ⽻������
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
//		arp.addMapping("t_blog", Blog.class);//���ݿ�ӳ�䣬��Ҫ�ڼ���PluginList֮ǰ������ã�����������
//		me.add(arp);
		
		// ����ActiveRecord���
//				ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
//				// ����ӳ���� MappingKit ���Զ����㶨
////				_MappingKit.mapping(arp);
//				arp.addMapping(tableName, modelClass)
//				me.add(arp);
		
		// ����ActiveRecord����������ݿ����ӳأ�������������
//		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
//		//����sql�ļ�����ʼ�ļ���
////		arp.setBaseSqlTemplatePath(PathKit.getWebRootPath() + "/WEB-INF");
//		/*
//		 * ������ sql �ļ���ŵĻ���·����ע�������� �뽫����·������Ϊ�� classpath �ĸ���
//		 * ���Խ� sql �ļ����� maven ��Ŀ�µ� resources ֮�£� ���������Զ���������� classpath ֮�£�
//		 * ��·���ɰ�ϲ����������
//		 */
//		arp.setBaseSqlTemplatePath(PathKit.getWebRootPath());
////		arp.addSqlTemplate("/demo.sql");
//		arp.addMapping("t_blog", Blog.class);
//		// ����ӳ���� MappingKit ���Զ����㶨 
//		me.add(arp);		
	}

	/**
	 * ����ȫ��������
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * ���ô�����
	 */
	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * ���������
	 */
	@Override
	public void afterJFinalStart() {
//		System.out.println("afterJFinalStart...");
	}
	
	/**
	 * ���ֹͣ��
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
		 * �ر�ע�⣺Eclipse ֮�½����������ʽ
		 */
//		JFinal.start("src/main/webapp", 8080, "/", 5);
		//�������ݿ⣬���°��eclipse
		JFinal.start("src/main/webapp", 9999, "/");
		/**
		 * �ر�ע�⣺IDEA ֮�½����������ʽ������ eclipse ֮���������һ������
		 */
//		JFinal.start("src/main/webapp", 8080, "/");
	}
}
