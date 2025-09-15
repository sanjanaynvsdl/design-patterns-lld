package ObeserverPattern;


import java.util.List;

public class Student {
    int cgr;
    String specalization;
    List<StudentObserver> studentSubscribers;

    public Student(List<StudentObserver> list) {
        this.studentSubscribers = list;
    }


    public void addSubscriber(StudentObserver newSubscriber) {
        studentSubscribers.add(newSubscriber);
    }


    public void updateCGR(int cgr) {
        this.cgr=cgr;
        System.out.println("[UPDATING CGR - STUDNET data update!]" + this.cgr);
        notifySub();
    }

    public void notifySub() {
        System.out.println("[UPDATING SUBSCRIBERS!]");
        for(StudentObserver sub :  this.studentSubscribers) {
            sub.update(this);
        }
    }    
}
