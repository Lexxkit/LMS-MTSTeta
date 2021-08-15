# Java backend tasks from MTS Teta summer school
1. Add HTML templates.
2. Add base Spring Boot project's structure
3. Add CRUD operations for Courses with Spring-MVC and Thymeleaf
    * output for all courses at /course;
    * output for the chosen course at /course/#id;
    * update Author or/and Title for the chosen course;
    * create a new course at /course/new;
    * delete the chosen course;
    * search a course by its title's prefix.
   
4. Add Spring-data-jpa support
   * add Lesson and User entities, repos, services and controllers;
   * assign/unassign Users to/from course;
   * create/delete lessons for courses.
   
5. Add Authentication and Authorization
   * add registration and login functionality;
   * add roles for users;
   * access to pages based on user's role.
   
6. Add Unit tests with JaCoCo report
   * Services' layer tests with H2 DB;
   * Controllers' layer tests with Mockito and MockMvc.
   
7. Add pictures for User Profile and Courses
   * OneToOne DB connection between Images tables and User/Course tables;
   * custom Internal Server Error exceptions handling;
