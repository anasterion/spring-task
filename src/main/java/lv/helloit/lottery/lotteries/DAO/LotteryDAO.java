package lv.helloit.lottery.lotteries.DAO;

import lv.helloit.lottery.lotteries.entities.Lottery;
import lv.helloit.lottery.lotteries.entities.LotteryResponse;

import java.util.Collection;

public interface LotteryDAO {
    LotteryResponse startRegistration(Lottery lottery);

    boolean checkIfDuplicate(String title);

    Collection<Lottery> getAll();
}
