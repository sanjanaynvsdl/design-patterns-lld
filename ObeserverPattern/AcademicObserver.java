package ObeserverPattern;

public class AcademicObserver implements StudentObserver {

    int graduationYear;

    @Override
    public void update(Student std) {
        System.out.println("[STUDENT UPDATED -> notified to academic sub]");
        generateScoreCard();
    }

    public void generateScoreCard() {
        System.out.println("[RE_GENERATED GRADE SHEET!]");
    }
    
}
