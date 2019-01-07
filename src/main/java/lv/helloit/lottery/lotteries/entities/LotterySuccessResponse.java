package lv.helloit.lottery.lotteries.entities;

import java.util.Objects;

public class LotterySuccessResponse extends LotteryResponse {
    private Long id;
    private String status;
    private String winnerCode;

    public LotterySuccessResponse() {
    }

    public LotterySuccessResponse(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public LotterySuccessResponse(String status) {
        this.status = status;
    }

    public LotterySuccessResponse(String status, String winnerCode) {
        this.status = status;
        this.winnerCode = winnerCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWinnerCode() {
        return winnerCode;
    }

    public void setWinnerCode(String winnerCode) {
        this.winnerCode = winnerCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotterySuccessResponse that = (LotterySuccessResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(status, that.status) &&
                Objects.equals(winnerCode, that.winnerCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, winnerCode);
    }

    @Override
    public String toString() {
        return "LotterySuccessResponse{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", winnerCode='" + winnerCode + '\'' +
                '}';
    }
}
