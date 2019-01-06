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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class LotteryDAOImplementation implements LotteryDAO {
    @Autowired
    private SessionFactory sessionFactory;

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
    public List<Lottery> getAll() {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Lottery> query = builder.createQuery(Lottery.class);
        query.select(query.from(Lottery.class));
        List<Lottery> lotteryList = session.createQuery(query).getResultList();

        session.close();
        return lotteryList;
    }

    @Override
    public boolean checkIfDuplicate(String value) {
        Session session = sessionFactory.openSession();

        Query<Lottery> query = session.createQuery("from Lottery l where l.title like '" + value + "'", Lottery.class);
        List<Lottery> employees = query.getResultList();

        if (employees.size() == 0) {
            session.close();
            return true;
        }

        session.close();
        return false;
    }

    @Override
    public Optional<Lottery> getById(Long id) {
        Session session = sessionFactory.openSession();
        Lottery lottery = session.get(Lottery.class, id);

        session.close();
        return Optional.ofNullable(lottery);
    }
}
