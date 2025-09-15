package ObeserverPattern;

public class CulturalActivityObserver  implements StudentObserver{
    boolean isActive;

    @Override
    public void update(Student std) {
         System.out.println("[STUDENT UPDATED -> notified to cultural sub]");
         totalActivites();
    }

    public void totalActivites() {
        System.out.println("[CULTURAL ACTIVITES ]");
    }

    
    
}
