public class PorscheCar extends Car{
    private String model;
    public PorscheCar(int year, double price, String model) {
        super(year, price);
        this.model = model;
    }
    @Override
    public String toString(){
        return "Car: " + "year=" + getYear() + ", price=" + getPrice()+", model="+model;
    }
}
