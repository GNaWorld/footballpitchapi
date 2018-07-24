import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.generator2.Generator;
import com.jfinal.plugin.druid.DruidPlugin;

import javax.sql.DataSource;

/**
 * �� demo �������Ϊ��ǳ�� jfinal �÷�����Ϊ�м�ֵ��ʵ�õ���ҵ���÷� ��� JFinal ���ֲ�:
 * http://jfinal.com/club
 * <p>
 * �����ݿ�����κα䶯ʱ������һ�� main ������������Ӧ�仯���д����ع�
 */
public class DbGenerator {
    public static DataSource getDataSource() {
        PropKit.use("a_little_config.txt");
        DruidPlugin druidPlugin = MainConfig.createDruidPlugin();
        druidPlugin.start();
        return druidPlugin.getDataSource();
    }

    public static void main(String[] args) {
        // base model ��ʹ�õİ���
        String baseModelPackageName = "com.model.base";
        // base model �ļ�����·��
        String baseModelOutputDir = PathKit.getWebRootPath()
                + "/src/main/java/com/model/base";

        // model ��ʹ�õİ��� (MappingKit Ĭ��ʹ�õİ���)
        String modelPackageName = "com.model";
        // model �ļ�����·�� (MappingKit �� DataDictionary �ļ�Ĭ�ϱ���·��)
        String modelOutputDir = baseModelOutputDir + "/..";

        // ����������
        Generator generator = new Generator(getDataSource(),
                baseModelPackageName, baseModelOutputDir, modelPackageName,
                modelOutputDir);
        // �����Ƿ�������ʽ setter ����
        generator.setGenerateChainSetter(false);
        // ��Ӳ���Ҫ���ɵı���
         generator.addExcludedTable("sys_sql_log");
        // �����Ƿ��� Model ������ dao ����
        generator.setGenerateDaoInModel(true);
        // �����Ƿ�������ʽ setter ����
        generator.setGenerateChainSetter(true);
        // �����Ƿ������ֵ��ļ�
        generator.setGenerateDataDictionary(false);
        // ������Ҫ���Ƴ��ı���ǰ׺��������modelName��������� "osc_user"���Ƴ�ǰ׺ "osc_"�����ɵ�model��Ϊ
        // "User"���� OscUser
        // generator.setRemovedTableNamePrefixes("t_");
        // ����
        generator.generate();
    }
}