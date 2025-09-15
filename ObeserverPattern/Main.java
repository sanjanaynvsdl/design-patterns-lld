package ObeserverPattern;

import java.util.ArrayList;
import java.util.List;

public class Main {
public static void main(String[] args) {
    AcademicObserver academicObv = new AcademicObserver();
    CulturalActivityObserver culturals = new CulturalActivityObserver();
    HostelObserver hstlObs = new HostelObserver();

    List<StudentObserver> observers = new ArrayList<>();
    observers.add(academicObv);
    observers.add(culturals);
    observers.add(hstlObs);


    Student std = new Student(observers);

    std.updateCGR(10);
}



    
}
