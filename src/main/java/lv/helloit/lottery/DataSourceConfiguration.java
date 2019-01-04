package lv.helloit.lottery;

import lv.helloit.lottery.lotteries.entities.Lottery;
import lv.helloit.lottery.participants.Participant;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
