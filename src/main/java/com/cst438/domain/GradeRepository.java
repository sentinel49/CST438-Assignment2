package com.cst438.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface GradeRepository extends CrudRepository<Grade, Integer> {

    @Query("select g from Grade g where g.assignment.assignmentId=:assignmentId and g.enrollment.enrollmentId=:enrollmentId")
    Optional<Grade> findByEnrollmentIdAndAssignmentId(int enrollmentId, int assignmentId);
}
