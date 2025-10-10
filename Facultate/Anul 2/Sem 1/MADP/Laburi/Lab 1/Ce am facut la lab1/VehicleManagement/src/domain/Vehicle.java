package domain;

import utils.MileageUnit;

public class Vehicle {

    private String licensePlate;
    private Double mileage;
    private Integer year;
    private MileageUnit mileageUnit;

    public Vehicle() {
        System.out.println("Default constructor");
    }

    public Vehicle(String licensePlate, Double newMileage, Integer year, MileageUnit mileageUnit) {
        this.licensePlate = licensePlate;
        mileage = newMileage;
        this.year = year;
        this.mileageUnit = mileageUnit;
    }

    public String getLicensePlate() {
        return this.licensePlate;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public void printVehicleDetails() {
        System.out.println("Vehicle has license plate:" + licensePlate + ", current mileage:" + mileage + " and unit:" +
                mileageUnit + " and it was produced in " + year);
    }
}
