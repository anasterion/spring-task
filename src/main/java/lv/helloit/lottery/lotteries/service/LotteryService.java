package lv.helloit.lottery.lotteries.service;

import lv.helloit.lottery.lotteries.DAO.LotteryDAO;
import lv.helloit.lottery.lotteries.entities.Lottery;
import lv.helloit.lottery.lotteries.entities.LotteryFailResponse;
import lv.helloit.lottery.lotteries.entities.LotteryResponse;
import lv.helloit.lottery.lotteries.entities.LotterySuccessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

        return lotteryList;
    }

    public boolean checkIfDuplicate(String value, String field) {
        return lotteryDAO.checkIfDuplicate(value, field);
    }

    public LotteryResponse stopRegistration(Long lotteryId) {
        Optional<Lottery> wrappedLottery = lotteryDAO.getById(lotteryId);

        if (wrappedLottery.isPresent()) {
            if (!wrappedLottery.get().getLotteryStatus().equals("IN PROGRESS")) {
                LOGGER.error("Failed to stop lottery " + wrappedLottery.get().getTitle() + " registration");
                return new LotteryFailResponse("Fail", "Lottery{" + wrappedLottery.get().getTitle() + "} already ended");
            }

            wrappedLottery.get().setLotteryStatus("ENDED");
            lotteryDAO.update(wrappedLottery.get());

            LOGGER.info("Successfully stopped lottery " + wrappedLottery.get().getTitle() + " registration");
            return new LotterySuccessResponse("OK");
        }

        LOGGER.error("Failed to stop lottery " + wrappedLottery.get().getTitle() + " registration");
        return new LotteryFailResponse("Fail", "Lottery with id - " + lotteryId + ", doesn't exist");
    }
}
