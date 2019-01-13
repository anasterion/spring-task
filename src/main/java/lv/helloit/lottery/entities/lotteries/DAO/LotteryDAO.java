package lv.helloit.lottery.entities.lotteries.DAO;

import lv.helloit.lottery.entities.lotteries.entities.Lottery;
import lv.helloit.lottery.entities.lotteries.entities.LotteryResponse;

import java.util.List;
import java.util.Optional;

public interface LotteryDAO {
    LotteryResponse startRegistration(Lottery lottery);

    boolean checkIfDuplicate(String value, String field);

    List<Lottery> getAll();

    Optional<Lottery> getById(Long id);

    void update(Lottery lottery);

    List<Lottery> getWithStatus(String status);

    List<Lottery> getInProgressForParticipant();

    Long getLotteryWithMaxId();
}
