package hairtist.dto;

import java.time.LocalDateTime;

public class TwoStringOneMoment {
    private String userName;
    private String email;
    private LocalDateTime orderTime;

    public TwoStringOneMoment(){

    }

    public TwoStringOneMoment(String userName, String email, LocalDateTime orderTime) {
        this.userName = userName;
        this.email = email;
        this.orderTime = orderTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }
}
