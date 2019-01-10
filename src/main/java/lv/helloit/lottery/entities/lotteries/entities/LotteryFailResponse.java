package lv.helloit.lottery.entities.lotteries.entities;

import java.util.Objects;

public class LotteryFailResponse extends LotteryResponse {
    private String status;
    private String reason;

    public LotteryFailResponse() {
    }

    public LotteryFailResponse(String status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotteryFailResponse that = (LotteryFailResponse) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, reason);
    }

    @Override
    public String toString() {
        return "LotteryFailResponse{" +
                "status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
