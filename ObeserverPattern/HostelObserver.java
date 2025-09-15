package ObeserverPattern;

public class HostelObserver  implements StudentObserver{

    int roomNo;

    @Override
    public void update(Student std) {
        System.out.println("[STUDENT UPDATED -> notified to hostel sub]");
        leave();
    }

    public void leave() {
        System.out.println("[Student is on leave!]");
    }
    
}
