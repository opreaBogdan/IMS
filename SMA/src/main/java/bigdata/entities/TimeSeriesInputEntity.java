package bigdata.entities;

import javax.persistence.*;

@Entity
@Table(name="prediction_results")
public class TimeSeriesInputEntity {

    @Id
    @Column(name = "token", nullable = false)
    private String token;

    public TimeSeriesInputEntity() {}

    public TimeSeriesInputEntity(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
