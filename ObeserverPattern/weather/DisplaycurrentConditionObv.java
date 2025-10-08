package weather;


public class DisplaycurrentConditionObv  implements IObserver{

    private float temp;
    private float pressure;
    private WeatherData weatherData;

    public DisplaycurrentConditionObv(WeatherData wd) {
        this.weatherData=wd;
        weatherData.registerSubscriber(this);
        //regirster to the weather data sub
    }

    @Override
    public void update() {

         System.out.println("----------[Previous Values]----------");
        System.out.println("[pressure is : ]" + this.pressure);
        System.out.println("[Temp is : ]" + this.temp);

        this.temp= weatherData.getTemp();
        this.pressure=weatherData.getPressure();
        
        display();
    }

    public void display() {
        System.out.println("======DISPLAY is of current ocnsidtion========================");
        System.out.println("[temperature is : ]"+this.temp);
        System.out.println("[pressure is : ]"+this.pressure);

    }
    
}
