package weather;

public interface ISubject {
    void registerSubscriber(IObserver ob);

    void removeSubscriber(IObserver ob);

    void notifySub();

}
