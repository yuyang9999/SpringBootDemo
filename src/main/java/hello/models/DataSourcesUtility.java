package hello.models;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import org.springframework.core.io.Resource;
import javax.sql.DataSource;

/**
 * Created by yangyu on 17/9/17.
 */

public class DataSourcesUtility {

    public static DataSource loadDataSource(String url, String userName, String password, String schemePath, String dataPath) {
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


}
