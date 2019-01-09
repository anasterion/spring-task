package lv.helloit.lottery.lotteries.service;

import lv.helloit.lottery.lotteries.DAO.LotteryDAO;
import lv.helloit.lottery.lotteries.entities.Lottery;
import lv.helloit.lottery.lotteries.entities.LotteryFailResponse;
import lv.helloit.lottery.lotteries.entities.LotteryResponse;
import lv.helloit.lottery.lotteries.entities.LotterySuccessResponse;
import lv.helloit.lottery.participants.DAO.ParticipantDAO;
import lv.helloit.lottery.participants.entities.Participant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LotteryService {
    private final static Logger LOGGER = LoggerFactory.getLogger(LotteryService.class);
    private final LotteryDAO lotteryDAO;
    private final ParticipantDAO participantDAO;

    @Autowired
    public LotteryService(LotteryDAO lotteryDAO, ParticipantDAO participantDAO) {
        this.lotteryDAO = lotteryDAO;
        this.participantDAO = participantDAO;
    }

    public LotteryResponse startRegistration(Lottery lottery) {
        LOGGER.info("Starting to create new lottery");

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD.MM.YY HH:mm");
        String strDate = simpleDateFormat.format(date);

        // to fix that input 02 is in java understanding 2, so it should save 2, 3 in DB. Not - 02, 0003 etc
        int tmpInt = Integer.valueOf(lottery.getLimit());
        lottery.setLimit(String.valueOf(tmpInt));

        lottery.setStartDate(strDate);
        lottery.setLotteryStatus("IN PROGRESS");

        LotteryResponse response = lotteryDAO.startRegistration(lottery);
        LOGGER.info("Created lottery -> " + lottery);

        return response;
    }

    public List<Lottery> getAll() {
        LOGGER.info("Getting lottery list");

        return lotteryDAO.getAll();
    }

    public boolean checkIfDuplicate(String value, String field) {
        return lotteryDAO.checkIfDuplicate(value, field);
    }

    public LotteryResponse stopRegistration(Long lotteryId) {
        Optional<Lottery> wrappedLottery = lotteryDAO.getById(lotteryId);

        if (wrappedLottery.isPresent()) {
            if (!wrappedLottery.get().getLotteryStatus().equals("IN PROGRESS")) {
                LOGGER.error("Failed to stop lottery registration with id " + lotteryId);
                return new LotteryFailResponse("Fail", "Lottery with id " + lotteryId + " already ended");
            }

            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD.MM.YY HH:mm");
            String strDate = simpleDateFormat.format(date);

            wrappedLottery.get().setEndDate(strDate);

            wrappedLottery.get().setLotteryStatus("ENDED");
            lotteryDAO.update(wrappedLottery.get());

            LOGGER.info("Successfully stopped lottery " + wrappedLottery.get().getTitle() + " registration");
            return new LotterySuccessResponse("OK");
        }

        LOGGER.error("Failed to stop lottery registration with id " + lotteryId);
        return new LotteryFailResponse("Fail", "Lottery with id - " + lotteryId + ", doesn't exist");
    }

    public List<Lottery> getWithStatus(String status) {
        return lotteryDAO.getWithStatus(status);
    }

    public LotteryResponse chooseWinner(Long id) {
        Optional<Lottery> wrappedLottery = lotteryDAO.getById(id);

        if (wrappedLottery.isPresent()) {

            if (wrappedLottery.get().getLotteryStatus().equals("IN PROGRESS")) {
                return new LotteryFailResponse("Fail", "Lottery with id " + id + " is still ongoing");
            }

            if (wrappedLottery.get().getParticipants().size() == 0) {
                return new LotteryFailResponse("Fail", "No participants found for lottery with id - " + id);
            }

            // check so winner can't be chosen multiple times
            if (!wrappedLottery.get().getParticipants().get(0).getStatus().equals("PENDING")) {
                return new LotteryFailResponse("Fail", "Lottery with id - " + id + " already has winner");
            }

            wrappedLottery.get().setLotteryStatus("FINISHED");
            lotteryDAO.update(wrappedLottery.get());

            for (Participant p : wrappedLottery.get().getParticipants()) {
                p.setStatus("LOOSE");
                participantDAO.update(p);
            }

            // applying equal chance winning logic
            LOGGER.info("Initial collection - " + wrappedLottery.get().getParticipants());
            Collections.shuffle(wrappedLottery.get().getParticipants());
            LOGGER.info("Shuffled collection - " + wrappedLottery.get().getParticipants());

            Random rand = new Random();
            int winnerNumber = rand.nextInt((wrappedLottery.get().getParticipants().size() - 1) + 1);
            LOGGER.info("Winner collection index number and code are - " + (winnerNumber + 1) + " : " + wrappedLottery.get().getParticipants().get(winnerNumber).getCode());

            wrappedLottery.get().getParticipants().get(winnerNumber).setStatus("WIN");
            participantDAO.update(wrappedLottery.get().getParticipants().get(winnerNumber));

            return new LotterySuccessResponse("OK", wrappedLottery.get().getParticipants().get(winnerNumber).getCode());
        }

        return new LotteryFailResponse("Fail", "Check available lotteries from list and try again");
    }

    public List<Lottery> getStatistics() {
        return lotteryDAO.getWithStatus("FINISHED");
    }

    public LotteryResponse getStatus(Long id, String email, String code) {
        Optional<Lottery> wrappedLottery = lotteryDAO.getById(id);

        if (wrappedLottery.isPresent()) {

            if (wrappedLottery.get().getParticipants().size() == 0) {
                return new LotteryFailResponse("ERROR", "Can't find such participant. Check input data");
            }

            for (Participant p : wrappedLottery.get().getParticipants()) {

                if (p.getCode().equals(code)) {
                    if (p.getEmail().equals(email)) {
                        return new LotterySuccessResponse(p.getStatus());
                    }
                }
            }

        }

        return new LotteryFailResponse("ERROR", "Can't find such participant. Check input data");
    }

    public List<Lottery> getInProgressForParticipant() {
        return lotteryDAO.getInProgressForParticipant();
    }
}
