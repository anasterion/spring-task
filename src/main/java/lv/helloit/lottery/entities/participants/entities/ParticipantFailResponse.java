package lv.helloit.lottery.entities.participants.entities;

import java.util.Objects;

public class ParticipantFailResponse extends ParticipantResponse {
    private String status;
    private String reason;

    public ParticipantFailResponse() {
    }

    public ParticipantFailResponse(String status, String reason) {
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
        ParticipantFailResponse that = (ParticipantFailResponse) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, reason);
    }

    @Override
    public String toString() {
        return "ParticipantFailResponse{" +
                "status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
