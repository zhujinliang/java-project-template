package com.dada.basic.dao.generate;

import com.dada.base.db.generate.AllBeanGenerator;
import com.dada.base.db.generate.JdbcProperties;
import com.dada.base.db.generate.table.MavenProjectInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *  自动生成 :     <br />
 *  1. BaseBean   <br />
 *  2. Bean       <br />
 *  3. Example    <br />
 *  4. Mapper.xml <br />
 *  5. Dao        <br />
 *  6. Service    <br />
 *
 *  其中 Bean , Dao , Service 若已存在, 则不覆盖
 *
 * Created by cook on 16/9/21.
 */
public class BeanGenerator {

    public static void main(String[] args) throws Exception {
        String usedDatabase = loadedProp.getProperty("used.database");
        String tableNames = loadedProp.getProperty("generate.table.names");

        JdbcProperties jdbcProp = createJdbcProp(usedDatabase);

        String _package = "com.dada.basic";
        String _groupId = "com.dada.basic";
        String _rootArtifactId = "dada-basic";
        MavenProjectInfo projectInfo = new MavenProjectInfo(rootDirectory, _package, _groupId, _rootArtifactId);

        AllBeanGenerator generator = new AllBeanGenerator(usedDatabase, tableNames, jdbcProp, projectInfo);

        generator.execGenerate();

        System.exit(1);
    }

    private static final String daoArtifactId = "dada-basic-dao";
    private static final String rootDirectory = getTopDir();

    private static final Properties loadedProp = loadJdbcProp("dev_jdbc.properties");

    private static Properties loadJdbcProp(String resourceName) {
        try {
            InputStream is = BeanGenerator.class.getClassLoader().getResourceAsStream(resourceName);
            Properties props = new Properties();
            props.load(is);
            is.close();
            return props;
        } catch (IOException e) {
            throw new RuntimeException("loadJdbcProp error.", e);
        }
    }

    private static JdbcProperties createJdbcProp(String dbName) throws IOException {
        String jdbcUrl = loadedProp.getProperty("jdbc.url");
        String username = loadedProp.getProperty("jdbc.username");
        String password = loadedProp.getProperty("jdbc.password");
        return new JdbcProperties(jdbcUrl.replace("{database}", dbName), username, password);
    }

    private static String getTopDir() {
        String propPath = BeanGenerator.class.getClassLoader().getResource("dev_jdbc.properties").getPath();
        System.out.println("propPath is " + propPath);

        String topDir = propPath.substring(0, propPath.indexOf("/" + daoArtifactId));
        System.out.println("topDir is " + topDir);

        return topDir;
    }

}
