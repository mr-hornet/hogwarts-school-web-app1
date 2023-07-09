-- liquibase formatted sql

-- changeset ashershnev:create_student_name_index
create index student_name_index on students (name);

-- changeset ashershnev:create_faculty_name_color_index
create index faculty_name_color_index on faculties (name, color);
