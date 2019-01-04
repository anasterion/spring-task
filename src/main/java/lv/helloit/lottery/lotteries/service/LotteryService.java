package lv.helloit.lottery.lotteries.service;

import lv.helloit.lottery.lotteries.DAO.LotteryDAO;
import lv.helloit.lottery.lotteries.controller.LotteryController;
import lv.helloit.lottery.lotteries.entities.Lottery;
import lv.helloit.lottery.lotteries.entities.LotteryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotteryService {
    private final static Logger LOGGER = LoggerFactory.getLogger(LotteryController.class);
    private final LotteryDAO lotteryDAO;

    @Autowired
    public LotteryService(LotteryDAO lotteryDAO) {
        this.lotteryDAO = lotteryDAO;
    }

    public LotteryResponse startRegistration(Lottery lottery) {
        LOGGER.info("Starting to create new lottery -> " + lottery);

        return lotteryDAO.startRegistration(lottery);
    }
}
