package hello.models;

import lombok.Getter;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import org.springframework.core.io.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangyu on 17/9/17.
 */

public class DataSourcesUtility {
    public static DataSource userDataSource;

    static {
        userDataSource = loadDataSource("jdbc:mysql://localhost:3306/users?createDatabaseIfNotExist=true",
                "root", "123456", "/userScheme.sql", null);
    }


    private static DataSource loadDataSource(String url, String userName, String password, String schemePath, String dataPath) {
        DriverManagerDataSource ret = new DriverManagerDataSource();
        ret.setDriverClassName("com.mysql.jdbc.Driver");
        ret.setUrl(url);
        ret.setUsername(userName);
        ret.setPassword(password);

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

        populator.addScript(new ClassPathResource(schemePath));
        if (dataPath != null) {
            populator.addScript(new ClassPathResource(dataPath));
        }

        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(ret);
        initializer.setDatabasePopulator(populator);

        initializer.afterPropertiesSet();

        return ret;
    }

    public static void main(String[] args) {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        String genCfg = "/DataBaseGenerator/mbgConfiguration.xml";
        File configFile = new File(DataSourcesUtility.class.getResource(genCfg).getFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try {
            config = cp.parseConfiguration(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLParserException e) {
            e.printStackTrace();
        }

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = null;
        try {
            myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        try {
            myBatisGenerator.generate(null);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(warnings);
    }


}
