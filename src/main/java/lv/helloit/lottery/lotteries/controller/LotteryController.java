package lv.helloit.lottery.lotteries.controller;

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

                if (!lotteryService.checkIfDuplicate(lottery.getTitle())) {
                    titleErrorMessage = "title : value should be unique\n";
                }
            }

            String errorMessage = titleErrorMessage + limitErrorMessage;
            return new LotteryFailResponse("Fail", errorMessage);
        }

        if (!lotteryService.checkIfDuplicate(lottery.getTitle())) {
            String errorMessage = "title : value should be unique\n" + limitErrorMessage;
            return new LotteryFailResponse("Fail", errorMessage);
        }

        return lotteryService.startRegistration(lottery);
    }

    @GetMapping(value = "/get-lottery-list")
    public Collection<Lottery> getAll() {
        return lotteryService.getAll();
    }
}
