package lv.helloit.lottery.participants.DAO;

import lv.helloit.lottery.participants.entities.Participant;
import lv.helloit.lottery.participants.entities.ParticipantResponse;

import java.util.Collection;
import java.util.List;

public interface ParticipantDAO {
    ParticipantResponse registerInLottery(Participant participant);

    List<Participant> getAll();
}
