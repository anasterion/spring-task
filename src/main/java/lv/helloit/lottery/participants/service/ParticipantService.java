package lv.helloit.lottery.participants.service;

import lv.helloit.lottery.lotteries.DAO.LotteryDAO;
import lv.helloit.lottery.lotteries.entities.Lottery;
import lv.helloit.lottery.participants.DAO.ParticipantDAO;
import lv.helloit.lottery.participants.entities.Participant;
import lv.helloit.lottery.participants.entities.ParticipantFailResponse;
import lv.helloit.lottery.participants.entities.ParticipantResponse;
import lv.helloit.lottery.participants.entities.ParticipantSuccessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ParticipantService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ParticipantService.class);
    private final ParticipantDAO participantDAO;
    private final LotteryDAO lotteryDAO;

    @Autowired
    public ParticipantService(ParticipantDAO participantDAO, LotteryDAO lotteryDAO) {
        this.participantDAO = participantDAO;
        this.lotteryDAO = lotteryDAO;
    }

    public ParticipantResponse assignLottery(Participant participant) {
        Optional<Lottery> wrappedLottery = lotteryDAO.getById(Long.valueOf(participant.getLotteryId()));

        if (wrappedLottery.isPresent()) {
            // check if limit is available
            wrappedLottery.get().setParticipantCount(String.valueOf(wrappedLottery.get().getParticipants().size() + 1));

            if (Integer.valueOf(wrappedLottery.get().getParticipantCount()) > (Integer.valueOf(wrappedLottery.get().getLimit()))) {
                return new ParticipantFailResponse("Fail", "Lottery{" + wrappedLottery.get().getTitle() + "} : limit reached, " +
                        "please choose another one from available list");
            // check if status is in progress
            } else if (!wrappedLottery.get().getLotteryStatus().equals("IN PROGRESS")) {
                return new ParticipantFailResponse("Fail", "Lottery: " + wrappedLottery.get().getTitle() + " is concluded, " +
                        "please choose another one with status - In progress");
            }

            // start participant registration
            LOGGER.info("Starting to register new participant");
            lotteryDAO.update(wrappedLottery.get());
            participant.setLottery(wrappedLottery.get());
            participant.setCode(generateCode(participant.getEmail()));
            participant.setStatus("PENDING");

            participantDAO.registerInLottery(participant);
            LOGGER.info("Created participant -> " + participant);

            return new ParticipantSuccessResponse("OK");
        }

        return new ParticipantFailResponse("Fail", "Lottery with id - " + participant.getLotteryId() + ", doesn't exist");
    }

    public String generateCode(String email) {
        String stringDate;

        while (true) {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DDMMYY");
            stringDate = simpleDateFormat.format(date);
            char[] tmpArray = email.toCharArray();

            if (tmpArray.length < 10) {
                stringDate = stringDate + "0" + tmpArray.length;
            } else {
                stringDate = stringDate + tmpArray.length;
            }

            Random rand = new Random();

            for (int i = 0; i < 8; i++) {
                stringDate = stringDate + rand.nextInt(10);
            }

            // checking generated code uniqueness
            if (!participantDAO.checkIfDuplicate(stringDate, "code")) {
                continue;
            }
            break;
        }

        return stringDate;
    }

    public List<Participant> getAll() {
        LOGGER.info("Getting participant list");
        return participantDAO.getAll();
    }

    public List<Participant> getWinnerList() {
        return participantDAO.getWinnerList();
    }

    public List<Participant> getConcludedLotteryParticipants() {
        return participantDAO.getConcludedLotteryParticipants();
    }
}
