package lv.helloit.lottery.participants.controller;

import lv.helloit.lottery.participants.entities.Participant;
import lv.helloit.lottery.participants.entities.ParticipantFailResponse;
import lv.helloit.lottery.participants.entities.ParticipantResponse;
import lv.helloit.lottery.participants.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
public class ParticipantController {
    private final ParticipantService participantService;

    @Autowired
    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ParticipantResponse registerInLottery(@Valid @RequestBody Participant participant, BindingResult bindingResult) {
        String emailErrorMessage = "";
        String ageErrorMessage = "";
        String idErrorMessage = "";

        if (bindingResult.hasErrors()) {
            int emailErrorCount = bindingResult.getFieldErrorCount("email");
            int ageErrorCount = bindingResult.getFieldErrorCount("age");
            int idErrorCount = bindingResult.getFieldErrorCount("lotteryId");

            if (emailErrorCount > 0) {
                emailErrorMessage = bindingResult.getFieldError("email").getField() + " : " +
                        bindingResult.getFieldError("email").getDefaultMessage() + "\n";
            }

            if (ageErrorCount > 0) {
                ageErrorMessage = bindingResult.getFieldError("age").getField() + " : " +
                        bindingResult.getFieldError("age").getDefaultMessage() + "\n";
            }

            if (idErrorCount > 0) {
                idErrorMessage = bindingResult.getFieldError("lotteryId").getField() + " : " +
                        bindingResult.getFieldError("lotteryId").getDefaultMessage() + "\n";
            }

            String errorMessage = emailErrorMessage + ageErrorMessage + idErrorMessage;
            return new ParticipantFailResponse("Fail", errorMessage);
        }

        ParticipantResponse response = participantService.assignLottery(participant);

        return response;
    }

    @GetMapping(value = "/get-participant-list")
    public Collection<Participant> getAll() {
        return participantService.getAll();
    }

    @GetMapping(value = "/get-participant-winner-list")
    public Collection<Participant> getWinnerList() {
        return participantService.getWinnerList();
    }

    @GetMapping(value = "/get-concluded-participant-list")
    public Collection<Participant> getConcludedLotteryParticipants() {
        return participantService.getConcludedLotteryParticipants();
    }
}
