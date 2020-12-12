DROP DATABASE IF EXISTS cr7_gabriella;
CREATE DATABASE IF NOT EXISTS cr7_gabriella;
USE cr7_gabriella;

drop table if exists students;
drop table if exists schoolclasses; 
drop table if exists teachers;
drop table if exists subjects;
drop table if exists teachers_schoolclasses;


CREATE TABLE schoolclasses(
  id int unsigned not null auto_increment primary key,
  name varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE students(
  id int unsigned not null auto_increment primary key,
  fk_schoolclasses_id int unsigned not null,
  surname varchar(50) NOT NULL,
  lastname varchar(50) NOT NULL,
  email varchar(50) NOT NULL,
foreign key (fk_schoolclasses_id) references schoolclasses(id) on delete cascade on update cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;




CREATE TABLE teachers(
  id int unsigned not null auto_increment primary key,
  surname varchar(50) NOT NULL,
  lastname varchar(50) NOT NULL,
  email varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE subjects(
  id int unsigned not null auto_increment primary key,
  name varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE teachers_schoolclasses(
  fk_schoolclasses_id int unsigned not null,
  fk_teachers_id int unsigned not null,
  fk_subjects_id int unsigned not null,
primary key(fk_schoolclasses_id, fk_teachers_id,fk_subjects_id),
foreign key (fk_schoolclasses_id) references schoolclasses(id) on delete cascade on update cascade,
foreign key (fk_teachers_id) references teachers(id) on delete cascade on update cascade,
foreign key ( fk_subjects_id) references subjects (id) on delete cascade on update cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO schoolclasses(name) VALUES 
('1A'), 
('1B'), 
('2A'), 
('2B'), 
('3A'),  
('3B'),  
('4A'),  
('4B');




INSERT INTO subjects (id, name) VALUES 
(NULL, 'Junior Java developer'), 
(NULL, 'Advanced Java developer'),
(NULL, 'Python'),
(NULL, 'Full Stack WEB Developer'),
(NULL, 'Mathematik'),
(NULL, 'Biologie'),
(NULL, 'Chemie'),
(NULL, 'Physik'),
(NULL, 'History')
;





INSERT INTO students (surname,lastname, email,fk_schoolclasses_id) VALUES 
('Peter', 'Steglich', 'peter.steglich@gmx.at',1), 
('Mohanad', 'Anadi', 'mohanad.anadi@gmx.at',1),
('Katrin', 'Steiner','katrin.steiner@gmx.at',1),
('Helena', 'Krivan','helena.krivan@gmx.at',1),
('Richard', 'Dumser','richard.dumser@gmx.at',2),
('Nikolas', 'Hoang','nikolas.hoang@gmx.at',2),
('Galwin', 'Meyer','galwin.meyer@gmx.at',2),
('Monika', 'Mahr','monika.mahr@gmx.at',3),
('Sabine', 'Koch','sabine.koch@gmx.at',3),
('Charlotte', 'Blank','charlotte.blank@gmx.at',3),
('Annette', 'Singer','annette.singer@gmx.at',4),
('Jhonny', 'Trent','jhonny.trent@gmx.at',4),
('Suzanne', 'Black','angelina.black@gmx.at',4),
('Walter', 'Hammer','walter.hammer@gmx.at',5),
('Lara', 'Radda','lara.radda@gmx.at',5),
('Siegfried', 'Kreisel','siegfried.kreisel@gmx.at',5),
('Selena', 'Mayer','selena.mayer@gmx.at',5),
('Anna', 'Hummel','anna.hummel@gmx.at',5)
;

INSERT INTO teachers (surname,lastname, email) VALUES 
('Carsten', 'Brodmann', 'carsten.brodmann@gmx.at'), 
('Mohanad', 'Albadri', 'mohanad.albadri@gmx.at'),
('Simon', 'Rivees', 'simon.rivees@gmx.at'),
('Larissa', 'Krawitz', 'larissa.krawitz@gmx.at'),
('Peter', 'Malik', 'peter.malik@gmx.at'),
('Michael', 'Steinbrecher', 'michael.steinbrecher@gmx.at'),
('Sharon', 'Stone', 'sharon.stone@gmx.at'),
('Isaac', 'Rabinovitch', 'isaac.rabinovitch@gmx.at');

INSERT INTO teachers_schoolclasses(fk_schoolclasses_id,fk_teachers_id, fk_subjects_id) VALUES
  (1,1,1),
  (1,3,2),
  (1,7,6),
  (2,1,1),
  (2,3,2),
  (2,7,6),
  (3,3,4),
  (3,6,9),
  (4,3,4),
  (4,6,9),
  (5,5,5),
  (6,5,5),
  (5,8,8);


COMMIT;



