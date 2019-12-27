package org.humki.baseadmin.core.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;

/**
 * 主数据源
 *
 * @author Kael
 * @date 2018/9/20 0020
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryCore",
        transactionManagerRef = "transactionManagerCore",
        basePackages = {"org.humki.baseadmin.core.repository"})
public class RepositoryConfigCore {


    private final JpaProperties jpaProperties;
    private final HibernateProperties hibernateProperties;
    private final DataSource coreDataSource;

    @Autowired
    public RepositoryConfigCore(
            JpaProperties jpaProperties,
            HibernateProperties hibernateProperties,
            @Qualifier("coreDataSource") DataSource coreDataSource) {
        this.jpaProperties = jpaProperties;
        this.coreDataSource = coreDataSource;
        this.hibernateProperties = hibernateProperties;
    }

    @Bean(name = "entityManagerCore")
    public EntityManager entityManagerCore(EntityManagerFactoryBuilder builder) {
        return Objects.requireNonNull(entityManagerFactoryCore(builder).getObject()).createEntityManager();
    }

    @Bean(name = "entityManagerFactoryCore")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryCore(EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
        return builder
                .dataSource(coreDataSource)
                .properties(properties)
                .packages("org.humki.baseadmin.core.pojo.po")
                .persistenceUnit("corePersistenceUnit")
                .build();
    }

    @Bean(name = "transactionManagerCore")
    PlatformTransactionManager transactionManagerCore(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactoryCore(builder).getObject()));
    }
}