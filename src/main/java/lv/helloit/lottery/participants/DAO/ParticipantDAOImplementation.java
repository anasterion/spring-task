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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class ParticipantDAOImplementation implements ParticipantDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public ParticipantDAOImplementation(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ParticipantResponse registerInLottery(Participant participant) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(participant);

        transaction.commit();
        session.close();

        return new ParticipantSuccessResponse("OK");
    }

    @Override
    public List<Participant> getAll() {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Participant> query = builder.createQuery(Participant.class);
        query.select(query.from(Participant.class));

        return session.createQuery(query).getResultList();
    }

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
}
