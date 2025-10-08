package weather;

public class Main {
    public static void main(String[] args) {

        WeatherData wd = new WeatherData(7,8,9);
        DisplaycurrentConditionObv currentConditions = new DisplaycurrentConditionObv(wd);
        DisplayForecastObv forecastObv  = new DisplayForecastObv(wd);
        wd.setMesurements(10, 10, 10);
        wd.setMesurements(18, 19, 20);



        
    }
    
}
