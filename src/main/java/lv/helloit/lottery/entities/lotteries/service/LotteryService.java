package lv.helloit.lottery.entities.lotteries.service;

import lv.helloit.lottery.entities.lotteries.DAO.LotteryDAO;
import lv.helloit.lottery.entities.lotteries.entities.*;
import lv.helloit.lottery.entities.participants.DAO.ParticipantDAO;
import lv.helloit.lottery.entities.participants.entities.Participant;
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
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD.MM.YY HH:mm");
        String strDate = simpleDateFormat.format(date);

        // to fix that input 02 is in java understanding 2, so it should save 2, 3 in DB. Not - 02, 0003 etc
        int tmpInt = Integer.valueOf(lottery.getLimit());
        lottery.setLimit(String.valueOf(tmpInt));

        lottery.setStartDate(strDate);
        lottery.setLotteryStatus("IN PROGRESS");
        lottery.setParticipantCount("0");

        LotteryResponse response = lotteryDAO.startRegistration(lottery);
        LOGGER.info("Successfully created lottery -> " + lottery);

        return response;
    }

    public List<Lottery> getAll() {
        LOGGER.info("Retrieving lottery list with status - IN PROGRESS");
        return lotteryDAO.getAll();
    }

    public boolean checkIfDuplicate(String value, String field) {
        return lotteryDAO.checkIfDuplicate(value, field);
    }

    public LotteryResponse stopRegistration(Long lotteryId) {
        Optional<Lottery> wrappedLottery = lotteryDAO.getById(lotteryId);

        if (wrappedLottery.isPresent()) {
            if (!wrappedLottery.get().getLotteryStatus().equals("IN PROGRESS")) {
                LOGGER.error("Failed to stop lottery registration with id " + lotteryId + ", reason: lottery already ended");
                return new LotteryFailResponse("Fail", "Lottery with id " + lotteryId + " already ended");
            }

            if (wrappedLottery.get().getParticipants().size() == 0) {
                LOGGER.error("Failed to stop lottery registration with id " + lotteryId + ", reason: no participants found");
                return new LotteryFailResponse("Fail", "No participants found for lottery with id - " + lotteryId);
            }

            wrappedLottery.get().setLotteryStatus("ENDED");
            lotteryDAO.update(wrappedLottery.get());
            LOGGER.info("Lottery id: " + lotteryId + "status updated to - ENDED");

            LOGGER.info("Successfully stopped lottery: " + wrappedLottery.get().getTitle() + " - registration");
            return new LotteryResponse("OK");
        }

        LOGGER.error("Failed to stop lottery registration with id " + lotteryId + ", reason: lottery doesn't exist");
        return new LotteryFailResponse("Fail", "Lottery with id - " + lotteryId + ", doesn't exist");
    }

    public List<Lottery> getWithStatus(String status) {
        LOGGER.info("Retrieving lottery list with status - " + status);
        return lotteryDAO.getWithStatus(status);
    }

    public LotteryResponse chooseWinner(Long id) {
        Optional<Lottery> wrappedLottery = lotteryDAO.getById(id);

        if (wrappedLottery.isPresent()) {

            if (wrappedLottery.get().getLotteryStatus().equals("IN PROGRESS")) {
                LOGGER.error("Failed to select winner for lottery id: " + id + ", reason: lottery is still ongoing");
                return new LotteryFailResponse("Fail", "Lottery with id " + id + " is still ongoing");
            }

            // check so winner can't be chosen multiple times
            if (!wrappedLottery.get().getParticipants().get(0).getStatus().equals("PENDING")) {
                LOGGER.error("Failed to select winner for lottery id: " + id + ", reason: lottery already has winner");
                return new LotteryFailResponse("Fail", "Lottery with id - " + id + " already has winner");
            }

            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD.MM.YY HH:mm");
            String strDate = simpleDateFormat.format(date);

            wrappedLottery.get().setEndDate(strDate);

            wrappedLottery.get().setLotteryStatus("FINISHED");
            lotteryDAO.update(wrappedLottery.get());
            LOGGER.info("Starting to choose lottery id: " + id + " winner");
            LOGGER.info("Lottery status changed to - FINISHED, end date has been set to " + strDate);
            LOGGER.info("Setting participant status to - LOOSE, before choosing the winner");

            for (Participant p : wrappedLottery.get().getParticipants()) {
                p.setStatus("LOOSE");
                participantDAO.update(p);
                LOGGER.info(p + " status has been changed to - LOOSE");
            }

            // applying equal chance winning logic
            LOGGER.info("Initial collection - " + wrappedLottery.get().getParticipants());
            Collections.shuffle(wrappedLottery.get().getParticipants());
            LOGGER.info("Shuffled collection - " + wrappedLottery.get().getParticipants());

            Random rand = new Random();
            int winnerNumber = rand.nextInt((wrappedLottery.get().getParticipants().size() - 1) + 1);
            LOGGER.info("Winner collection index number and code are - " + winnerNumber + " : " + wrappedLottery.get().getParticipants().get(winnerNumber).getCode());

            wrappedLottery.get().getParticipants().get(winnerNumber).setStatus("WIN");
            participantDAO.update(wrappedLottery.get().getParticipants().get(winnerNumber));
            LOGGER.info("Setting winner " + wrappedLottery.get().getParticipants().get(winnerNumber) + " status to - WIN");

            LOGGER.info("Successfully concluded lottery with id: " + id);
            return new LotteryWinnerSuccessResponse("OK", wrappedLottery.get().getParticipants().get(winnerNumber).getCode());
        }

        LOGGER.error("Failed to select winner for lottery id: " + id + ", reason: lottery doesn't exist");
        return new LotteryFailResponse("Fail", "Check available lotteries from list and try again");
    }

    public List<Lottery> getStatistics() {
        LOGGER.info("Retrieving lottery list with status - FINISHED");
        return lotteryDAO.getWithStatus("FINISHED");
    }

    public LotteryResponse getStatus(Long id, String email, String code) {
        Optional<Lottery> wrappedLottery = lotteryDAO.getById(id);

        if (wrappedLottery.isPresent()) {
        LOGGER.info("Retrieving info about participant status");

            if (wrappedLottery.get().getParticipants().size() == 0) {
                LOGGER.error("Failed to retrieve info about participant email: " + email + ", code: " + code);
                return new LotteryFailResponse("ERROR", "Can't find such participant. Check input data");
            }

            for (Participant p : wrappedLottery.get().getParticipants()) {

                if (p.getCode().equals(code)) {
                    if (p.getEmail().equals(email)) {
                        LOGGER.info("Successfully retrieved info about participant email: " + email + ", code: " + code + ". Status - " + p.getStatus());
                        return new LotteryResponse(p.getStatus());
                    }
                }
            }

        }

        LOGGER.error("Failed to retrieve info about participant email: " + email + ", code: " + code);
        return new LotteryFailResponse("ERROR", "Can't find such participant. Check input data");
    }

    public List<Lottery> getInProgressForParticipant() {
        LOGGER.info("Retrieving lottery list with status - IN PROGRESS");
        return lotteryDAO.getInProgressForParticipant();
    }
}
