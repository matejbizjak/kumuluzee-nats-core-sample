package si.matejbizjak.natscore.sample.api.entity;

import java.time.LocalDateTime;
import java.util.Optional;

public class Demo {

    private String name;
    private Double doubleNumber;
    private LocalDateTime dateTime;
    private int intNumber;
    public Demo() {
    }

    public Demo(String name, Double doubleNumber, LocalDateTime dateTime, int intNumber) {
        this.name = name;
        this.doubleNumber = doubleNumber;
        this.dateTime = dateTime;
        this.intNumber = intNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDoubleNumber() {
        return doubleNumber;
    }

    public void setDoubleNumber(Double doubleNumber) {
        this.doubleNumber = doubleNumber;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getIntNumber() {
        return intNumber;
    }

    public void setIntNumber(int intNumber) {
        this.intNumber = intNumber;
    }
}
