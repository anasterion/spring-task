package lv.helloit.lottery.lotteries.entities;

import java.util.Objects;

public class Lottery {
    private Long id;
    private String title;
    private Long limit;
    private String status;
    private String reason;

    public Lottery() {
    }

    public Lottery(String title, Long limit, String status, String reason) {
        this.title = title;
        this.limit = limit;
        this.status = status;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
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
        Lottery lottery = (Lottery) o;
        return Objects.equals(id, lottery.id) &&
                Objects.equals(title, lottery.title) &&
                Objects.equals(limit, lottery.limit) &&
                Objects.equals(status, lottery.status) &&
                Objects.equals(reason, lottery.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, limit, status, reason);
    }

    @Override
    public String toString() {
        return "Lottery{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", limit=" + limit +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
