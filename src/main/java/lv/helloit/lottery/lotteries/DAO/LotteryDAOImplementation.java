package lv.helloit.lottery.lotteries.DAO;

import lv.helloit.lottery.lotteries.entities.Lottery;
import lv.helloit.lottery.lotteries.entities.LotteryResponse;
import lv.helloit.lottery.lotteries.entities.LotterySuccessResponse;
import org.springframework.stereotype.Repository;

@Repository
public class LotteryDAOImplementation implements LotteryDAO {
    @Override
    public LotteryResponse startRegistration(Lottery lottery) {

        return new LotterySuccessResponse(1L, "OK");
    }
}
