package com.relations.relations.one2many.lecturer;

import java.util.Collection;
public interface LecturerServiceInterface {
    Lecturer createLecturer(Lecturer lecturer);
    Lecturer getLecturer(Long id);
    Collection<Lecturer> getAllLecturers();
}
