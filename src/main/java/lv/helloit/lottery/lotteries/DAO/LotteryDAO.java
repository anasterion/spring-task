package lv.helloit.lottery.lotteries.DAO;

import lv.helloit.lottery.lotteries.entities.Lottery;
import lv.helloit.lottery.lotteries.entities.LotteryResponse;

import java.util.List;
import java.util.Optional;

public interface LotteryDAO {
    LotteryResponse startRegistration(Lottery lottery);

    boolean checkIfDuplicate(String value, String field);

    List<Lottery> getAll();

    Optional<Lottery> getById(Long id);

    void update(Lottery lottery);
}
