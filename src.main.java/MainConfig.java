import com.controller.api.IndexController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.template.Engine;

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
		me.add("/", IndexController.class);
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

	public static void main(String[] args) {
		/**
		 * �ر�ע�⣺Eclipse ֮�½����������ʽ
		 */
		JFinal.start("src/main/webapp", 8080, "/", 5);
		/**
		 * �ر�ע�⣺IDEA ֮�½����������ʽ������ eclipse ֮���������һ������
		 */
//		JFinal.start("src/main/webapp", 8080, "/");
	}
}
