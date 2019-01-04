package lv.helloit.lottery.lotteries.entities;

import java.util.Objects;

public class LotterySuccessResponse extends LotteryResponse {
    private Long id;
    private String status;

    public LotterySuccessResponse() {
    }

    public LotterySuccessResponse(Long id, String status) {
        this.id = id;
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotterySuccessResponse that = (LotterySuccessResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status);
    }

    @Override
    public String toString() {
        return "LotterySuccessResponse{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
