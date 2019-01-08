package lv.helloit.lottery.participants.DAO;

import lv.helloit.lottery.participants.entities.Participant;
import lv.helloit.lottery.participants.entities.ParticipantResponse;

import java.util.Collection;
import java.util.List;

public interface ParticipantDAO {
    ParticipantResponse registerInLottery(Participant participant);

    List<Participant> getAll();

    boolean checkIfDuplicate(String value, String field);

    void update(Participant participant);

    List<Participant> getWinnerList();

    List<Participant> getConcludedLotteryParticipants();
}
