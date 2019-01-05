package lv.helloit.lottery.lotteries.DAO;

import lv.helloit.lottery.lotteries.entities.Lottery;
import lv.helloit.lottery.lotteries.entities.LotteryResponse;
import lv.helloit.lottery.lotteries.entities.LotterySuccessResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class LotteryDAOImplementation implements LotteryDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public LotteryDAOImplementation(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public LotteryResponse startRegistration(Lottery lottery) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(lottery);

        transaction.commit();
        session.close();

        return new LotterySuccessResponse(lottery.getId(), "OK");
    }

    @Override
    public Collection<Lottery> checkIfDuplicate(String title) {
        Session session = sessionFactory.openSession();

        Query<Lottery> query = session.createQuery("from Lottery l where l.title like '" + title + "'", Lottery.class);
        List<Lottery> employees = query.getResultList();

        session.close();
        return employees;
    }
}
