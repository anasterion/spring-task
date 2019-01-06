package lv.helloit.lottery.participants.service;

import lv.helloit.lottery.lotteries.DAO.LotteryDAO;
import lv.helloit.lottery.lotteries.entities.Lottery;
import lv.helloit.lottery.lotteries.service.LotteryService;
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

    public ParticipantResponse registerInLottery(Participant participant) {
        LOGGER.info("Starting to register new participant");

        ParticipantResponse response = participantDAO.registerInLottery(participant);
        LOGGER.info("Created participant -> " + participant);

        return response;
    }

    public ParticipantResponse assignLottery(Participant participant) {
        Optional<Lottery> wrappedLottery = lotteryDAO.getById(participant.getLotteryId());

        if (wrappedLottery.isPresent()) {
            participant.setLottery(wrappedLottery.get());
            participant.setCode(generateCode(participant.getEmail()));

            participantDAO.registerInLottery(participant);

            return new ParticipantSuccessResponse("OK");
        }

        return new ParticipantFailResponse("Fail", "Lottery with id - " + participant.getLotteryId() + ", doesn't exist");
    }

    public String generateCode(String email) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DDMMYY");
        String stringDate = simpleDateFormat.format(date);
        char[] tmpArray = email.toCharArray();

        if (tmpArray.length < 10) {
            stringDate = stringDate + "0" + tmpArray.length;
        } else {
            stringDate = stringDate + tmpArray.length;
        }

        Random rand = new Random();
        String tmpString = "";

        for (int i = 0; i < 8; i++) {
            tmpString = tmpString + rand.nextInt(10);
        }

        stringDate = stringDate + tmpString;

        return stringDate;
    }

    public List<Participant> getAll() {
        LOGGER.info("Printing participant list");
        return participantDAO.getAll();
    }
}
