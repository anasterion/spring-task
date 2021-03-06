package lv.helloit.lottery;

import lv.helloit.lottery.entities.lotteries.entities.Lottery;
import lv.helloit.lottery.entities.participants.entities.Participant;
import org.hibernate.SessionFactory;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Configuration
public class DataSourceConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .addAnnotatedClass(Lottery.class)
                .addAnnotatedClass(Participant.class)
                .configure()
                .buildSessionFactory();
    }
}
