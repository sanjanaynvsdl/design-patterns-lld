

1. Weather Application: In this scenario, there's a central weather data object that holds information like temperature, atmospheric pressure, and precipitation. Multiple display objects (such as mobile app displays, web app displays, and public LED screens) need to be updated whenever the weather data changes. The challenge is to design a system where these displays are automatically notified and updated when there's any change in the weather data.

2. Student Report System: In this scenario, there's a system for digitizing student results. There are different components:

A class/object that stores scores for different components of a subject (like assignments, mid-term exams, final exams).
Another class/object that handles course-level mapping, storing grades and attendance for each course.
The problem is that when there's an update in one component (e.g., a student's midterm score is corrected from 50% to 80%), this change should automatically trigger updates in related components (like recalculating the overall grade, updating CGR, reassessing eligibility for scholarships or placements).

- In both cases, the challenge is to design a system where changes in one object automatically notify and update other related objects, without tightly coupling these objects together. This is exactly the kind of problem that the Observer pattern is designed to solve.


- running an infinte look and keep on checking it, which is highly in-efficient

### Youtube Example
Scenario: You have a YouTube channel you're interested in and want to watch new videos as soon as they're uploaded.

Inefficient Approach (Polling):

You keep checking the YouTube channel repeatedly.
You decide a frequency: every hour, minute, day, or week.
You manually check if any new videos have been uploaded.
Efficient Approach (Observer Pattern):
You subscribe to the channel.
Whenever they add a new video, you automatically get a notification.
You don't need to keep checking the channel manually.




YouTube example:

### Polling:

A technique where a client repeatedly checks a resource for updates or changes.
The client initiates the request for information at regular intervals.
Characteristics:
Can be inefficient as it may result in many unnecessary checks.
May miss updates if the polling interval is too long.
Can be resource-intensive if the polling frequency is high.
Also known as an "eager loading" approach.

### Push (Observer Pattern):

A mechanism where the resource (subject) notifies interested parties (observers) when there's an update.
The subject initiates the communication when a change occurs.
Characteristics:
More efficient as it eliminates unnecessary checks.
Provides real-time or near real-time updates.
Reduces network traffic and processing overhead.
Also referred to as a "lazy loading" approach.
Implements a publish-subscribe model.

--------------------------------------- <br>
```java
//now lets say class A, B, C uses this subject data they need to get notified whenever theres a change, so we pass them in the constructor


class Subject{
    A a;
    B b;
    C c;

    public Subject(A a, B b, C c) {
        this.a=a;
        this.b=b;
        this.c=c;
    }

    public setCGR() {
        //on setting we call the respective functions to update like

        a.calculatePercentage();
        b.generateGradeSheet();
    }

    //but, now what's the issue?, 
    //constructor explosion, 
    //there can be many classes(or subscribers) who use this subject we cannot write that many constructors

    //there might be a case wehre a function needs some arguments like a.calculateCGR(might need arguments)
    
    
    //to add a new subscriber in future, we need to add a constructor and then modify all the setters
}


```