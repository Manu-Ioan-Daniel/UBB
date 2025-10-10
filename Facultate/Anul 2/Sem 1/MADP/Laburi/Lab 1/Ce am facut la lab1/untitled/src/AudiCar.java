public class AudiCar  extends Car {
    private String market;
    public AudiCar(int year, double price,String market){
        super(year,price);
        this.market=market;
    }
    public String getMarket() {
        return market;
    }
    @Override
    public String toString(){
        return "Car: " + "year=" + getYear() + ", price=" + getPrice()+", market="+market;
    }
}