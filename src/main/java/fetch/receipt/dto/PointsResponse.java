package fetch.receipt.dto;

public class PointsResponse {

    private Long points;

    public PointsResponse() {}

    public PointsResponse(Long points) {
        this.points = points;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }
}
