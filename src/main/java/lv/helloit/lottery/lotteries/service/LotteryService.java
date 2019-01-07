package lv.helloit.lottery.lotteries.service;

import lv.helloit.lottery.lotteries.DAO.LotteryDAO;
import lv.helloit.lottery.lotteries.entities.Lottery;
import lv.helloit.lottery.lotteries.entities.LotteryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD.MM.YY HH:mm");
        String strDate = simpleDateFormat.format(date);

        lottery.setStartDate(strDate);
        lottery.setLotteryStatus("IN PROGRESS");

        LotteryResponse response = lotteryDAO.startRegistration(lottery);
        LOGGER.info("Created lottery -> " + lottery);

        return response;
    }

    public List<Lottery> getAll() {
        LOGGER.info("Getting lottery list");

        List<Lottery> lotteryList = lotteryDAO.getAll();

        for (Lottery l : lotteryList) {
            l.setLotteryCapacity(l.getParticipants().size());
        }

        return lotteryList;
    }

    public boolean checkIfDuplicate(String value) {
        return lotteryDAO.checkIfDuplicate(value);
    }
}
