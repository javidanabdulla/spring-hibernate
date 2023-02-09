package az.spring.hibernate.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "az.spring.hibernate")
@PropertySource("db/database.properties")
@EnableTransactionManagement // db ilə olan commit rollback kimi əməliyyatları avtomatlaşdırmaq üçün olan annotationdur
public class SpringHibernateConfig {

    @Bean
    // DataSource database ilə hansı vasitələrlə qoşulmağımızı tanıtmaq üçündür
    public DataSource dataSource (DataBaseConfig dataBaseConfig) {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(dataBaseConfig.getDriverClassname());
        driverManagerDataSource.setUrl(dataBaseConfig.getUrl());
        driverManagerDataSource.setUsername(dataBaseConfig.getUsername());
        driverManagerDataSource.setPassword(dataBaseConfig.getPassword());

        return driverManagerDataSource;
    }

    @Bean
    // Database ilə connection/session açmaq üçündür (sessiya yaradır)
    public LocalSessionFactoryBean sessionFactory (DataSource dataSource) {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource); // hansı db ilə connection olacağını (sessiya yaradacağını) dataSource-dan götürür
        localSessionFactoryBean.setPackagesToScan("az.spring.hibernate.model"); // bu sessiyada hansı Entity db ilə əməliyyat edəcəyini burdan götürür
        localSessionFactoryBean.setHibernateProperties(hibernateProperties()); // bu sessiyada db ilə hansı növ əməliyyatları hansı dialekt vasitəsi ilə edəcəyini deyir

        return localSessionFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager (SessionFactory sessionFactory) {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager(); // hibernate vasitəsi ilə sessiya yaratmaq üçün obyekt yaradılır
        hibernateTransactionManager.setSessionFactory(sessionFactory); // bu method vasitəsi ilə hansı sessiya ilə db-də əməliyyatlar icra edə biləcəyimizi göstərir

        return hibernateTransactionManager;
    }

    private Properties hibernateProperties () {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create"); // db table ilə hansı növ əməliyyatı icra edə biləcəyimizi qeyd edirik
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect"); // db server ilə hansı dildə danışacağımızı bildiririk

        return properties;
    }

}
