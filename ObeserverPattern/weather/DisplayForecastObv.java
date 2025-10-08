package weather;

public class DisplayForecastObv implements IObserver {

    private float humidity;
    private float pressure;

    private WeatherData weatherData;

    public DisplayForecastObv(WeatherData wd) {
       this.weatherData=wd;
        this.weatherData.registerSubscriber(this);
    }

    @Override
    public void update() {

        System.out.println("-----------[Previous Values of display forecast]---------");
        System.out.println("[Humidity is : ]" + this.humidity);
        System.out.println("[Pressure is : ]" + this.pressure);

        this.humidity = weatherData.getHumidity();
        this.pressure = weatherData.getPressure();
       
        display();


    }

    public void display() {
        System.out.println("======display Weather forecast===========");
        System.out.println("[Humidity is : ]"+this.humidity);
        System.out.println("[Pressure is : ]"+this.pressure);
    }
}
