ALTER TABLE students
    ADD CONSTRAINT age_check check ( age >= 16 );

ALTER TABLE students
    ADD CONSTRAINT name_unique unique(name);

ALTER TABLE students
    ALTER COLUMN name set not null;

ALTER TABLE faculties
    ADD CONSTRAINT name_color_unique unique(name, color);

ALTER TABLE students
    ALTER COLUMN age set default 20;