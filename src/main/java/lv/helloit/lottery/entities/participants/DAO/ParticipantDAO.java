package lv.helloit.lottery.entities.participants.DAO;

import lv.helloit.lottery.entities.participants.entities.Participant;
import lv.helloit.lottery.entities.participants.entities.ParticipantResponse;

import java.util.List;

public interface ParticipantDAO {
    ParticipantResponse registerInLottery(Participant participant);

    List<Participant> getAll();

    boolean checkIfDuplicate(String value, String field);

    void update(Participant participant);

    List<Participant> getWinnerList();

    List<Participant> getConcludedLotteryParticipants();
}
