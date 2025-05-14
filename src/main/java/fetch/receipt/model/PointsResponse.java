package fetch.receipt.model;

public class PointsResponse {
    private long points;

    public PointsResponse() {
    }

    public PointsResponse(long points) {
        this.points = points;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }
}
