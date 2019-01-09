package lv.helloit.lottery.lotteries.controller;

import lv.helloit.lottery.IdValidationWrapper;
import lv.helloit.lottery.lotteries.entities.Lottery;
import lv.helloit.lottery.lotteries.entities.LotteryFailResponse;
import lv.helloit.lottery.lotteries.entities.LotteryResponse;
import lv.helloit.lottery.lotteries.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
public class LotteryController {
    private final LotteryService lotteryService;

    @Autowired
    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @PostMapping(value = "/start-registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public LotteryResponse startRegistration(@Valid @RequestBody Lottery lottery, BindingResult bindingResult) {
        String titleErrorMessage = "";
        String limitErrorMessage = "";


        if (bindingResult.hasErrors()) {
            int titleErrorCount = bindingResult.getFieldErrorCount("title");
            int limitErrorCount = bindingResult.getFieldErrorCount("limit");

            if (titleErrorCount > 0) {
                titleErrorMessage = bindingResult.getFieldError("title").getField() + " : " +
                        bindingResult.getFieldError("title").getDefaultMessage() + "\n";
            }

            if (limitErrorCount > 0) {
                limitErrorMessage = bindingResult.getFieldError("limit").getField() + " : " +
                        bindingResult.getFieldError("limit").getDefaultMessage() + "\n";

                if (!lotteryService.checkIfDuplicate(lottery.getTitle(), "title")) {
                    titleErrorMessage = "title : value should be unique\n";
                }
            }

            String errorMessage = titleErrorMessage + limitErrorMessage;
            return new LotteryFailResponse("Fail", errorMessage);
        }

        if (!lotteryService.checkIfDuplicate(lottery.getTitle(), "title")) {
            String errorMessage = "title : value should be unique\n" + limitErrorMessage;
            return new LotteryFailResponse("Fail", errorMessage);
        }

        return lotteryService.startRegistration(lottery);
    }

    @GetMapping(value = "/get-lottery-list")
    public Collection<Lottery> getAll() {
        return lotteryService.getAll();
    }

    @GetMapping(value = "get-lottery-list/{status}")
    public Collection<Lottery> getWithStatus(@PathVariable String status) {
        return lotteryService.getWithStatus(status);
    }

    @SuppressWarnings("Duplicates")
    @PostMapping(value = "/stop-registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public LotteryResponse stopRegistration(@Valid @RequestBody IdValidationWrapper id, BindingResult bindingResult) {
        String idErrorMessage = "";

        if (bindingResult.hasErrors()) {
            int idErrorCount = bindingResult.getFieldErrorCount("id");

            if (idErrorCount > 0) {
                idErrorMessage = bindingResult.getFieldError("id").getField() + " : " +
                        bindingResult.getFieldError("id").getDefaultMessage() + "\n";
            }

            return new LotteryFailResponse("Fail", idErrorMessage);
        }

        return lotteryService.stopRegistration(Long.valueOf(id.getId()));
    }

    @SuppressWarnings("Duplicates")
    @PostMapping(value = "/choose-winner", produces = MediaType.APPLICATION_JSON_VALUE)
    public LotteryResponse chooseWinner(@Valid @RequestBody IdValidationWrapper id, BindingResult bindingResult) {
        String idErrorMessage = "";

        if (bindingResult.hasErrors()) {
            int idErrorCount = bindingResult.getFieldErrorCount("id");

            if (idErrorCount > 0) {
                idErrorMessage = bindingResult.getFieldError("id").getField() + " : " +
                        bindingResult.getFieldError("id").getDefaultMessage() + "\n";
            }

            return new LotteryFailResponse("Fail", idErrorMessage);
        }

        return lotteryService.chooseWinner(Long.valueOf(id.getId()));
    }

    @GetMapping(value = "stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Lottery> getStatistics() {
        return lotteryService.getStatistics();
    }

    @GetMapping(value = "status", produces = MediaType.APPLICATION_JSON_VALUE)
    public LotteryResponse getStatus(@RequestParam String id, @RequestParam String email, @RequestParam String code) {
        id = id.trim();
        email = email.trim();
        code = code.trim();

        if (id.equals("") || id.isEmpty()) {
            return new LotteryFailResponse("ERROR", "Check if all fields are populated");
        } else if (email.equals("") || email.isEmpty()) {
            return new LotteryFailResponse("ERROR", "Check if all fields are populated");
        } else if (code.equals("") || code.isEmpty()) {
            return new LotteryFailResponse("ERROR", "Check if all fields are populated");
        }

        Long longId;

        try {
            longId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            return new LotteryFailResponse("ERROR", "Check if id is correct");
        }

        return lotteryService.getStatus(longId, email, code);
    }

    @GetMapping(value = "/get-lottery-list-for-participant")
    public Collection<Lottery> getInProgressForParticipant() {
        return lotteryService.getInProgressForParticipant();
    }
}
