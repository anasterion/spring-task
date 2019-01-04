package lv.helloit.lottery.lotteries.DAO;

import lv.helloit.lottery.lotteries.entities.Lottery;
import lv.helloit.lottery.lotteries.entities.LotteryResponse;

public interface LotteryDAO {
    LotteryResponse startRegistration(Lottery lottery);
}
