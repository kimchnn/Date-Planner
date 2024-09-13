# My Personal Project: Date Planner

## Background

**Overview**

The Date Planner is designed to help users plan and manage activities and outings with their partners, friends, or family. Users can maintain a list of activities they want to do, including places or restaurants they want to visit. The application allows users to filter activities by category and location. Once filtered, users can add these activities to their date itinerary, set dates and times, and receive reminders. 

**Target Users**

This application is intended for people who want to organize and maximize their social activities and outings. It is ideal for anyone who enjoys planning dates, outings, and experiences with others, ensuring that they make the most of their time together.

**Why is this project of interest to me?**

Sharing memorable experiences with loved ones is important, and this project aims to make planning these experiences easier and more enjoyable. As someone who values spending quality time with friends and loved ones, creating a tool that enhances social planning and helps make every outing special is both exciting and fulfilling.

## User Stories
- As a user, I want to be able to add activities to my collection, specifying their name, type and location 
- As a user, I want to be able to view the list of activity names in my collection
- As a user, I want to be able to remove activities from my collection
- As a user, I want to be able to select an activity and schedule a date and time for it, adding it to a list of scheduled activities
- As a user, I want to be able to filter scheduled activities by date and view the list of activity names in the filtered list
- As a user, I want to be able to save my activity collection, including my scheduled activities, to file (if I so choose)
- As a user, when I start the application, I want to be given the option to load my activity collection, including my scheduled activities, from file (if I so choose)

# Instructions for Grader
- First, please select the "Get started" button to start the application.
- Generate the first required action related to the user story "adding activities to the collection, specifying their name, type and location" by clicking the "Start New Session" or the "Load Last Session" button in the pop-up window. Then, select the "Add a New Activity" button on the home window. To create the activity, fill in the name, category, and location text fields, and click the "Add" button. 
- Generate the second required action related to the user story "viewing the list of activity names in the activity collection" by clicking the "View Activities" button on the home window. Note that if no activities have been added, an error message will pop up.
- If you have activities already added to your collection, you can generate the third action related to the user story "removing activities from the activity collection" by clicking the "View Activities" button on the home window. Then, click on an activity in the list, and click the "Select" button. In the pop-up window, click the "Remove" button to remove the activity from the collection.
- If you have activities already added to your collection, you can generate the fifth action related to the user story "scheduling a date and time for it, adding it to a list of scheduled activities" by clicking the "View Activities" button on the home window. Then click on an activity in the list, and click the "Select" button. In the pop-up window, click the "Schedule" button and enter in the date and time in the correct format to schedule the selected activity. 
- If you have activities added to your collection and at least one of them have been scheduled, You can generate the sixth action related to the user story "filtering scheduled activities by date and viewing the list of activity names in the filtered list" by clicking the "View Scheduled Activities" button on the home window. In the pop-up window, enter the date you wish to view scheduled activities for and click the "Submit" button to view the scheduled activities based on the specified date. Note if you wish to view all scheduled activities, you can do so by clicking "View All Scheduled Activities".
- You can locate my visual component on the first window with the "Get Started" button. I have also incorporated visual components on the home window, with the images added to the three buttons ("Add a new Activity", "View Activities" and "View Scheduled Activities").
- You can save the state of my application by clicking the "Save" button on the home window.
- You can reload the state of my application by clicking the "Load Last Session" button on the "Getting Started" pop-up window.

# Phase 4: Task 2
Mon Aug 05 22:02:27 PDT 2024  
New Activity (Hiking) added to the activity collection.  
Mon Aug 05 22:02:34 PDT 2024  
New Activity (Skating) added to the activity collection.  
Mon Aug 05 22:02:35 PDT 2024  
Viewed all activities in the activity collection.  
Mon Aug 05 22:02:38 PDT 2024  
Hiking was removed from the activity collection.  
Mon Aug 05 22:02:48 PDT 2024
Skating was scheduled.

# Phase 4: Task 3
**Refactoring Opportunities**
- I would introduce a new abstract class to capture shared behaviour between "ViewActivitiesWindow" and "ViewScheduledWindow", including methods for initializing components and handling user interactions. This refactoring would reduce code duplication and improve maintainability. By extending the new abstract class, any new view classes or modifications to the existing ones would adhere to a consistent structure, streamlining future development and maintenance.  
- I would refactor DatePlannerGUI and DatePlannerUI to decouple the GUI from the console UI methods. This would improve the overall architecture by ensuring that the GUI operates independently of the console UI. This separation of concerns would make the code more modular, allowing the project to become more maintainable and easier to extend in the future.

- I have reflected these two changes in the latest version.