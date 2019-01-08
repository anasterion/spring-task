package lv.helloit.lottery.participants.DAO;

import lv.helloit.lottery.participants.entities.Participant;
import lv.helloit.lottery.participants.entities.ParticipantResponse;
import lv.helloit.lottery.participants.entities.ParticipantSuccessResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ParticipantDAOImplementation implements ParticipantDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public ParticipantDAOImplementation(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public ParticipantResponse registerInLottery(Participant participant) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(participant);

        transaction.commit();
        session.close();

        return new ParticipantSuccessResponse("OK");
    }

    @SuppressWarnings("Duplicates")
    @Override
    public List<Participant> getAll() {
        Session session = sessionFactory.openSession();

        Query<Participant> query = session.createQuery("from Participant p where p.status like 'PENDING' order by p.id", Participant.class);
        List<Participant> participantList = query.getResultList();

        session.close();
        return participantList;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public boolean checkIfDuplicate(String value, String field) {
        Session session = sessionFactory.openSession();

        Query<Participant> query = session.createQuery("from Participant p where p." + field + " like '" + value + "'", Participant.class);
        List<Participant> employees = query.getResultList();

        if (employees.size() == 0) {
            session.close();
            return true;
        }

        session.close();
        return false;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void update(Participant participant) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        session.update(participant);

        tx.commit();
        session.close();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public List<Participant> getWinnerList() {
        Session session = sessionFactory.openSession();

        Query<Participant> query = session.createQuery("from Participant p where p.status like 'WIN' order by p.id", Participant.class);
        List<Participant> participantList = query.getResultList();

        session.close();
        return participantList;
    }

    @Override
    public List<Participant> getConcludedLotteryParticipants() {
        Session session = sessionFactory.openSession();

        Query<Participant> query = session.createQuery("from Participant p where p.status not like 'PENDING' order by p.id", Participant.class);
        List<Participant> participantList = query.getResultList();

        session.close();
        return participantList;
    }
}
