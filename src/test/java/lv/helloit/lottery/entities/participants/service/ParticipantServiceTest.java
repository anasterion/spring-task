package lv.helloit.lottery.entities.participants.service;

import lv.helloit.lottery.entities.lotteries.DAO.LotteryDAO;
import lv.helloit.lottery.entities.lotteries.entities.Lottery;
import lv.helloit.lottery.entities.lotteries.entities.LotteryFailResponse;
import lv.helloit.lottery.entities.lotteries.entities.LotteryResponse;
import lv.helloit.lottery.entities.lotteries.service.LotteryService;
import lv.helloit.lottery.entities.participants.DAO.ParticipantDAO;
import lv.helloit.lottery.entities.participants.entities.Participant;
import lv.helloit.lottery.entities.participants.entities.ParticipantFailResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParticipantServiceTest {
    @Autowired
    private ParticipantService participantService;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private ParticipantDAO participantDAO;

    @Autowired
    private LotteryDAO lotteryDAO;

    @Test
    public void shouldNotCreateParticipantIfLotteryDoesntExist() {
        Lottery l = new Lottery("Test132", "15", null, null, null, null, null);
        lotteryService.startRegistration(l);

        long nonExistingId = lotteryDAO.getLotteryWithMaxId() + 1;
        Participant p = new Participant(String.valueOf(nonExistingId), "test@test.com", "27", null, null, null);

        ParticipantFailResponse failResponse = (ParticipantFailResponse) participantService.assignLottery(p);

        assertThat(failResponse.getReason()).isEqualTo("Lottery with id - " + nonExistingId + ", doesn't exist");
    }

    @Test
    public void shouldNotCreateParticipantIfLotteryStatusIsOtherThanInProgress() {
        Lottery l = new Lottery("Test132", "15", null, null, null, null, null);
        lotteryService.startRegistration(l);
        String id = String.valueOf(l.getId());

        l.setLotteryStatus("ENDED");
        lotteryDAO.update(l);

        Participant p = new Participant(id, "test@test.com", "27", null, null, null);
        ParticipantFailResponse failResponse = (ParticipantFailResponse) participantService.assignLottery(p);

        assertThat(failResponse.getReason()).isEqualTo("Lottery: " + l.getTitle() + " is concluded, please choose another one with status - In progress");

        l.setLotteryStatus("FINISHED");
        lotteryDAO.update(l);

        ParticipantFailResponse participantFailResponse = (ParticipantFailResponse) participantService.assignLottery(p);

        assertThat(participantFailResponse.getReason()).isEqualTo("Lottery: " + l.getTitle() + " is concluded, please choose another one with status - In progress");
    }

    @Test
    public void shouldGenerate16DigitCodeDuringParticipantRegistration() {
        Lottery l = new Lottery("Test132", "15", null, null, null, null, null);
        lotteryService.startRegistration(l);
        String id = String.valueOf(l.getId());

        Participant p = new Participant(id, "test@test.com", "27", null, null, null);
        participantService.assignLottery(p);

        char[] tmp = p.getCode().toCharArray();

        assertThat(tmp.length).isEqualTo(16);
    }

    @Test
    public void shouldCreateAndPopulateParticipantFieldsWithValues() {
        Lottery l = new Lottery("Test132", "15", null, null, null, null, null);
        lotteryService.startRegistration(l);
        String id = String.valueOf(l.getId());

        Participant p = new Participant(id, "test@test.com", "27", null, null, null);
        participantService.assignLottery(p);

        List<Participant> participants = participantService.getAll();

        assertThat(participants.size()).isNotEqualTo(0);
        assertThat(p.getCode()).isNotNull();
        assertThat(p.getStatus()).isNotNull();
        assertThat(p.getLottery()).isNotNull();
    }

    @Test
    public void shouldNotRetrieveStatusIfParticipantWasNotRegisteredForLottery() {
        Lottery l = new Lottery("Test132", "15", null, null, null, null, null);
        lotteryService.startRegistration(l);
        Long id = l.getId();

        LotteryFailResponse failResponse = (LotteryFailResponse) lotteryService.getStatus(id, "test", "1923847261723122");

        assertThat(failResponse.getReason()).isEqualTo("Can't find such participant. Check input data");
    }

    @Test
    public void shouldNotRetrieveStatusIfLotteryDoesNotExist() {
        Lottery l = new Lottery("Test132", "15", null, null, null, null, null);
        lotteryService.startRegistration(l);
        long nonExistingId = lotteryDAO.getLotteryWithMaxId() + 1;

        LotteryFailResponse failResponse = (LotteryFailResponse) lotteryService.getStatus(nonExistingId, "test", "1923847261723122");

        assertThat(failResponse.getReason()).isEqualTo("Can't find such participant. Check input data");
    }

    @Test
    public void shouldRetrieveParticipantStatusIfRegisteredForLottery() {
        Lottery l = new Lottery("Test132", "15", null, null, null, null, null);
        lotteryService.startRegistration(l);
        String id = String.valueOf(l.getId());

        Participant p = new Participant(id, "test@test.com", "27", null, null, null);
        participantService.assignLottery(p);

        LotteryResponse successResponse = lotteryService.getStatus(Long.valueOf(id), p.getEmail(), p.getCode());

        assertThat(successResponse.getStatus()).isEqualTo("PENDING");

        p.setStatus("WIN");
        participantDAO.update(p);
        successResponse = lotteryService.getStatus(Long.valueOf(id), p.getEmail(), p.getCode());

        assertThat(successResponse.getStatus()).isEqualTo("WIN");

        p.setStatus("LOOSE");
        participantDAO.update(p);
        successResponse = lotteryService.getStatus(Long.valueOf(id), p.getEmail(), p.getCode());

        assertThat(successResponse.getStatus()).isEqualTo("LOOSE");
    }
}