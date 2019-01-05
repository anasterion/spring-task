package lv.helloit.lottery.lotteries.service;

import lv.helloit.lottery.lotteries.DAO.LotteryDAO;
import lv.helloit.lottery.lotteries.entities.Lottery;
import lv.helloit.lottery.lotteries.entities.LotteryFailResponse;
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
        String reason = validateLotteryRegistration(lottery);

        if (!reason.equals("")) {
            return new LotteryFailResponse("Fail", reason);
        }

        lottery.setStartDate(new Date());
        lottery.setLotteryStatus("IN PROGRESS");
        return lotteryDAO.startRegistration(lottery);
    }

    private String validateLotteryRegistration(Lottery lottery) {
        String reason = "";

        if (!lotteryDAO.checkIfDuplicate(lottery.getTitle())) {
            reason = reason + "Lottery title must be unique\n";

            return validateLimitField(lottery, reason);
        } else if (!validateLimitField(lottery, reason).equals("")) {
            reason = validateLimitField(lottery, reason);

            if (!lotteryDAO.checkIfDuplicate(lottery.getTitle())) {
                reason = reason + "Lottery title must be unique\n";
            }

            return reason;
        }

        return validateLimitField(lottery, reason);
    }

    private String validateLimitField(Lottery lottery, String reason) {
        try {
            int tmpNum = Integer.parseInt(lottery.getLimit());

            if (tmpNum <= 0) {
                reason = reason + "Lottery limit must be positive number\n";
            }
        } catch (NumberFormatException ex) {
            reason = reason + "Lottery limit must be a number\n";
        }

        return reason;
    }

    public Collection<Lottery> getAll() {
        return lotteryDAO.getAll();
    }
}
