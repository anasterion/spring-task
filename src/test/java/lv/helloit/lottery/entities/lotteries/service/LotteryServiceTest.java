package lv.helloit.lottery.entities.lotteries.service;

import lv.helloit.lottery.entities.lotteries.DAO.LotteryDAO;
import lv.helloit.lottery.entities.lotteries.entities.*;
import lv.helloit.lottery.entities.participants.DAO.ParticipantDAO;
import lv.helloit.lottery.entities.participants.entities.Participant;
import lv.helloit.lottery.entities.participants.service.ParticipantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryServiceTest {
    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private LotteryDAO lotteryDAO;

    @Autowired
    private ParticipantDAO participantDAO;

    @Test
    public void shouldCreateNewLotteryAndPopulateFieldsWithDefaultValues() {
        Lottery l = new Lottery("Test132", "15", null, null, null, null, null);
        lotteryService.startRegistration(l);

        List<Lottery> lotteryList = lotteryService.getAll();

        assertThat(lotteryList).isNotEmpty();
        assertThat(l.getStartDate()).isNotNull();
        assertThat(l.getEndDate()).isNull();
        assertThat(l.getParticipants()).isNull();
        assertThat(l.getTitle()).isEqualTo("Test132");
        assertThat(l.getLimit()).isEqualTo("15");
        assertThat(l.getLotteryStatus()).isEqualTo("IN PROGRESS");
        assertThat(l.getParticipantCount()).isEqualTo("0");
    }

    @Test
    public void lotteryTitleShouldBeUnique() {
        Lottery l = new Lottery("Test132", "15", null, null, null, null, null);
        lotteryService.startRegistration(l);

        boolean check = lotteryService.checkIfDuplicate("Test132", "title");

        assertThat(check).isFalse();
    }

    @Test
    public void shouldNotStopLotteryIfStatusIsNotInProgress() {
        Lottery l = new Lottery("Test132", "15", null, null, null, null, null);
        lotteryService.startRegistration(l);

        l.setLotteryStatus("ENDED");
        lotteryDAO.update(l);

        Long id = l.getId();
        LotteryFailResponse statusEnded = (LotteryFailResponse) lotteryService.stopRegistration(id);

        assertThat(statusEnded.getReason()).isEqualTo("Lottery with id " + id + " already ended");

        l.setLotteryStatus("FINISHED");
        lotteryDAO.update(l);
        LotteryFailResponse statusFinished = (LotteryFailResponse) lotteryService.stopRegistration(id);

        assertThat(statusFinished.getReason()).isEqualTo("Lottery with id " + id + " already ended");
    }

    @Test
    public void shouldNotStopLotteryIfNoParticipants() {
        Lottery l = new Lottery("Test132", "15", null, null, null, null, null);
        lotteryService.startRegistration(l);

        Long id = l.getId();
        LotteryFailResponse failResponse = (LotteryFailResponse) lotteryService.stopRegistration(id);

        assertThat(failResponse.getReason()).isEqualTo("No participants found for lottery with id - " + id);
    }

    @Test
    public void shouldNotStopNonExistingLottery() {
        Lottery l = new Lottery("Test132", "15", null, null, null, null, null);
        lotteryService.startRegistration(l);

        long nonExistingId = lotteryDAO.getLotteryWithMaxId() + 1;

        LotteryFailResponse failResponse = (LotteryFailResponse) lotteryService.stopRegistration(nonExistingId);

        assertThat(failResponse.getReason()).isEqualTo("Lottery with id - " + nonExistingId + ", doesn't exist");
    }

    @Test
    public void shouldStopLotteryWithParticipantsAndStatusInProgress() {
        Lottery l = new Lottery("Test132", "15", null, null, null, null, null);
        lotteryService.startRegistration(l);
        Long id = l.getId();

        Participant p = new Participant(String.valueOf(id), "test@test.com", "27", null, null, null);
        participantService.assignLottery(p);

        LotteryResponse successResponse = lotteryService.stopRegistration(id);

        assertThat(successResponse.getStatus()).isEqualTo("OK");
    }

    @Test
    public void shouldNotChooseWinnerIfLotteryStatusInProgress() {
        Lottery l = new Lottery("Test132", "15", null, null, null, null, null);
        lotteryService.startRegistration(l);
        Long id = l.getId();

        LotteryFailResponse failResponse = (LotteryFailResponse) lotteryService.chooseWinner(id);

        assertThat(failResponse.getReason()).isEqualTo("Lottery with id " + id + " is still ongoing");
    }

    @Test
    public void shouldNotChooseWinnerIfAlreadyHasOne() {
        Lottery l = new Lottery("Test132", "15", null, null, null, null, null);
        lotteryService.startRegistration(l);
        Long id = l.getId();

        Participant p = new Participant(String.valueOf(id), "test@test.com", "27", null, "WIN", null);
        participantService.assignLottery(p);
        lotteryService.stopRegistration(id);

        p.setStatus("WIN");
        participantDAO.update(p);

        LotteryFailResponse failResponse = (LotteryFailResponse) lotteryService.chooseWinner(id);

        assertThat(failResponse.getReason()).isEqualTo("Lottery with id - " + id + " already has winner");
    }

    @Test
    public void shouldNotChooseWinnerIfLotteryDoesntExist() {
        Lottery l = new Lottery("Test132", "15", null, null, null, null, null);
        lotteryService.startRegistration(l);

        Long id = l.getId();

        long nonExistingId = lotteryDAO.getLotteryWithMaxId() + 1;

        Participant p = new Participant(String.valueOf(id), "test@test.com", "27", null, "WIN", null);
        participantService.assignLottery(p);
        lotteryService.stopRegistration(id);

        LotteryFailResponse failResponse = (LotteryFailResponse) lotteryService.chooseWinner(nonExistingId);

        assertThat(failResponse.getReason()).isEqualTo("Check available lotteries from list and try again");
    }

    @Test
    public void shouldConcludeLotteryAndChooseWinner() {
        Lottery l = new Lottery("Test132", "15", null, null, null, null, null);
        lotteryService.startRegistration(l);

        Long id = l.getId();

        Participant p = new Participant(String.valueOf(id), "test@test.com", "27", null, null, null);
        participantService.assignLottery(p);
        lotteryService.stopRegistration(id);

        LotteryWinnerSuccessResponse successResponse = (LotteryWinnerSuccessResponse) lotteryService.chooseWinner(id);
        p.setStatus("WIN");
        participantDAO.update(p);

        assertThat(p.getStatus()).isEqualTo("WIN");
        assertThat(successResponse.getStatus()).isEqualTo("OK");
    }

    @Test
    public void shouldRetrieveListWithStatusInProgress() {
        Lottery l = new Lottery("Test132", "15", null, null, null, null, null);
        lotteryService.startRegistration(l);

        List<Lottery> lotteryList = lotteryService.getAll();

        assertThat(lotteryList.size()).isNotEqualTo(0);
    }

    @Test
    public void shouldRetrieveListWithStatus() {
        Lottery l = new Lottery("Test132", "15", null, null, null, null, null);
        lotteryService.startRegistration(l);

        l.setLotteryStatus("test");
        lotteryDAO.update(l);

        List<Lottery> lotteryList = lotteryService.getWithStatus("test");

        assertThat(lotteryList.size()).isNotEqualTo(0);
    }
}