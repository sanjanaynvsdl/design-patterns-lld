package weather;

import java.util.ArrayList;
import java.util.List;

public class WeatherData implements ISubject {

    List<IObserver> subscribers;
    private float temp;
    private float pressure;
    private float humidity;

    public WeatherData(float temp, float pressure, float humidity) {
        this.temp=temp;
        this.pressure=pressure;
        this.humidity=humidity;
        subscribers= new ArrayList<>();
    }

    @Override
    public void registerSubscriber(IObserver ob) {
        System.out.println("[added new sub : ] "+ob);
        this.subscribers.add(ob);
    }

    @Override
    public void removeSubscriber(IObserver ob) {
        int index = subscribers.indexOf(ob);
        System.out.println("[removed a sub : ] " + ob);
        this.subscribers.remove(index);
    }

    @Override
    public void notifySub() {
        System.out.println("[updating all the subs]");
        for(IObserver ob:subscribers) {
            ob.update();
        }
    }

    public float getPressure() {
        return this.pressure;
    }

    public float getTemp() {
        return this.temp;
    }

    public float getHumidity() {
        return this.humidity;
    }

    public void setMesurements(float temp, float pressure, float humidity) {
        this.temp=temp;
        this.pressure=pressure;
        this.humidity=humidity;
        notifySub();
    }
    
}
