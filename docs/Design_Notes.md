# Design Notes

## Why ArrayList and not array

Array size is fixed when you create it. In this app we don't know how many students or courses will be added, so with an array I would have to pick a size and then copy to a bigger array when it gets full. ArrayList grows on its own and has add, remove, size, isEmpty methods already, so it's much easier. With generics (`ArrayList<Student>`) it also only allows the correct type.

The list methods in services return a new copy of the list so Main can't directly modify the service's list.

## Where static is used

- `IdGenerator` has static counters (studentIdCounter, courseIdCounter, enrollmentIdCounter) and static methods to get the next id. Counter needs to be shared across the whole app so ids don't repeat, so static makes sense here. The constructor is private since we never need an object of it.
- `InputValidator` methods are static because they don't depend on any object data, they just check the input passed to them.
- In `Main` the services and Scanner are static because main() is static and we only need one of each.

Everything else is instance level, like each Student has its own name, batch, active etc.

## Where inheritance is used

`Person` is the base class with id, firstName, lastName, email. `Student` and `Trainer` extend it.

- Student adds batch and active
- Trainer adds expertise
- Both call `super(...)` in the constructor to set the common fields
- Both override `getDisplayName()`. Person returns just the name, Student adds the batch and Trainer adds the expertise.

What I got from this:
- Common fields and getters/setters are written only once in Person
- In the "View All People" option I put students and trainers in one `List<Person>` and call `getDisplayName()`. The right version gets called for each object at runtime (polymorphism), no need for instanceof checks.
- If another type of person is needed later it can just extend Person.

## Other points

- Overloading: Student has 2 constructors (with and without email). StudentService has 2 `addStudent` methods for the same.
- Custom exceptions: `EntityNotFoundException` when an id is not found, `InvalidInputException` for wrong input. Main catches them and prints the message so the program doesn't crash.
- Deactivating a student only sets active to false instead of deleting, so enrollments still point to a valid student.
- Used an enum for enrollment status so there are no spelling mistakes like "Completed" vs "COMPLETED".
- Main only shows the menu, reads input and calls the services. All the checks (inactive student, inactive course, duplicate enrollment) are in the service classes.
