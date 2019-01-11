package lv.helloit.lottery.entities.lotteries.entities;

import java.util.Objects;

public class LotteryResponse {
    private String status;

    public LotteryResponse() {
    }

    public LotteryResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotteryResponse that = (LotteryResponse) o;
        return Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }

    @Override
    public String toString() {
        return "LotteryResponse{" +
                "status='" + status + '\'' +
                '}';
    }
}
