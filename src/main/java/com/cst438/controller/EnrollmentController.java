package com.cst438.controller;


import com.cst438.domain.*;
import com.cst438.dto.EnrollmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class EnrollmentController {

    // instructor downloads student enrollments for a section, ordered by student name
    // user must be instructor for the section

    @Autowired
    EnrollmentRepository enrollmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TermRepository termRepository;

    @GetMapping("/sections/{sectionNo}/enrollments")
    public List<EnrollmentDTO> getEnrollments(
            @PathVariable("sectionNo") int sectionNo) {

        // TODO
        //  hint: use enrollment repository findEnrollmentsBySectionNoOrderByStudentName method
        List<Enrollment> enrollments = enrollmentRepository.findEnrollmentsBySectionNoOrderByStudentName(sectionNo);

        List<EnrollmentDTO> dto_list = new ArrayList<>();
        for (Enrollment e : enrollments) {
            /*
            User instructor = null;
            if (e.getInstructorEmail()!=null)  {
                instructor = userRepository.findByEmail(e.getInstructorEmail());
            }
        }
        */
            dto_list.add(new EnrollmentDTO(
                    e.getEnrollmentId(),
                    e.getGrade(),
                    e.getUser().getId(),
                    e.getUser().getName(),
                    e.getUser().getEmail(),
                    e.getSection().getCourse().getCourseId(),
                    e.getSection().getCourse().getTitle(),
                    e.getSection().getSecId(),
                    e.getSection().getSectionNo(),
                    e.getSection().getBuilding(),
                    e.getSection().getRoom(),
                    e.getSection().getTimes(),
                    e.getSection().getCourse().getCredits(),
                    e.getSection().getTerm().getYear(),
                    e.getSection().getTerm().getSemester()
            ));

        }
        return dto_list;
        //  remove the following line when done
        // return null;
    }

    // instructor uploads enrollments with the final grades for the section
    // user must be instructor for the section
    @PutMapping("/enrollments")
    public void updateEnrollmentGrade(@RequestBody List<EnrollmentDTO> dto_list) {
        // TODO
        // For each EnrollmentDTO in the list
        //  find the Enrollment entity using enrollmentId
        //  update the grade and save back to database
        for (EnrollmentDTO e : dto_list) {
            Enrollment enrollment = enrollmentRepository.findById(e.enrollmentId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
            enrollment.setGrade(e.grade());
            enrollmentRepository.save(enrollment);
        }
    }
}
