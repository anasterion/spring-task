package lv.helloit.lottery.entities.participants.entities;

import java.util.Objects;

public class ParticipantSuccessResponse extends ParticipantResponse {
    private String status;

    public ParticipantSuccessResponse() {
    }

    public ParticipantSuccessResponse(String status) {
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
        ParticipantSuccessResponse that = (ParticipantSuccessResponse) o;
        return Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }

    @Override
    public String toString() {
        return "ParticipantSuccessResponse{" +
                "status='" + status + '\'' +
                '}';
    }
}
