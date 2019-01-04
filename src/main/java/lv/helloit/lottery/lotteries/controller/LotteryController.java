package lv.helloit.lottery.lotteries.controller;

import lv.helloit.lottery.lotteries.entities.Lottery;
import lv.helloit.lottery.lotteries.entities.LotteryResponse;
import lv.helloit.lottery.lotteries.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LotteryController {
    private final LotteryService lotteryService;

    @Autowired
    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @PostMapping(value = "/start-registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public LotteryResponse startRegistration(@RequestBody Lottery lottery) {

        return lotteryService.startRegistration(lottery);
    }
}
