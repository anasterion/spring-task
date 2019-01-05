package lv.helloit.lottery.lotteries.service;

import lv.helloit.lottery.lotteries.DAO.LotteryDAO;
import lv.helloit.lottery.lotteries.entities.Lottery;
import lv.helloit.lottery.lotteries.entities.LotteryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
public class LotteryService {
    private final static Logger LOGGER = LoggerFactory.getLogger(LotteryService.class);
    private final LotteryDAO lotteryDAO;

    @Autowired
    public LotteryService(LotteryDAO lotteryDAO) {
        this.lotteryDAO = lotteryDAO;
    }

    public LotteryResponse startRegistration(Lottery lottery) {
        LOGGER.info("Starting to create new lottery");

        lottery.setStartDate(new Date());
        lottery.setLotteryStatus("IN PROGRESS");
        return lotteryDAO.startRegistration(lottery);
    }

    public Collection<Lottery> getAll() {
        return lotteryDAO.getAll();
    }
}
