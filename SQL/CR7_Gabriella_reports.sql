USE cr7_gabriella;

/*all students of a specific class, given class ID*/
SELECT 
students.id,
students.surname,
students.lastname, 
students.email
 FROM students
 inner join schoolclasses on students.fk_schoolclasses_id=schoolclasses.id 
where schoolclasses.id=2
order by students.lastname, students.surname;

/*all students of a specific calss given the name of the class*/
SELECT 
students.id,
students.surname,
students.lastname, 
students.email
 FROM students
 inner join schoolclasses on students.fk_schoolclasses_id=schoolclasses.id 
where schoolclasses.name="1B"
order by students.lastname, students.surname;

commit;
