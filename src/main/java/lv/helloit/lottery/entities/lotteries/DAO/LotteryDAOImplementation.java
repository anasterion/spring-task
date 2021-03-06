package lv.helloit.lottery.entities.lotteries.DAO;

import lv.helloit.lottery.entities.lotteries.entities.Lottery;
import lv.helloit.lottery.entities.lotteries.entities.LotteryResponse;
import lv.helloit.lottery.entities.lotteries.entities.LotterySuccessResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LotteryDAOImplementation implements LotteryDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public LotteryDAOImplementation(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("Duplicates")
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

        Query<Lottery> query = session.createQuery("from Lottery l where l.lotteryStatus like 'IN PROGRESS' order by l.id", Lottery.class);
        List<Lottery> lotteryList = query.getResultList();

        session.close();
        return lotteryList;
    }

    @Override
    public List<Lottery> getWithStatus(String status) {
        Session session = sessionFactory.openSession();

        Query<Lottery> query = session.createQuery("from Lottery l where l.lotteryStatus like '" + status + "' order by l.id", Lottery.class);
        List<Lottery> lotteryList = query.getResultList();

        session.close();
        return lotteryList;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public boolean checkIfDuplicate(String value, String field) {
        Session session = sessionFactory.openSession();

        Query<Lottery> query = session.createQuery("from Lottery l where l." + field + " like '" + value + "'", Lottery.class);
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

    @SuppressWarnings("Duplicates")
    @Override
    public void update(Lottery lottery) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        session.update(lottery);

        tx.commit();
        session.close();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public List<Lottery> getInProgressForParticipant() {
        Session session = sessionFactory.openSession();

        Query<Lottery> query = session.createQuery("from Lottery l where l.lotteryStatus like 'IN PROGRESS' and l.participantCount not like l.limit order by l.id", Lottery.class);
        List<Lottery> lotteryList = query.getResultList();

        session.close();
        return lotteryList;
    }

    @Override
    public Long getLotteryWithMaxId() {
        Session session = sessionFactory.openSession();

        Query<Lottery> query = session.createQuery("from Lottery l where l.id = (select max(id) from Lottery)", Lottery.class);
        List<Lottery> lotteryList = query.getResultList();

        return lotteryList.get(0).getId();
    }
}
