package com.aniket.placementcell.repository;

import com.aniket.placementcell.entity.AppliedJob;
import com.aniket.placementcell.entity.JobPosting;
import com.aniket.placementcell.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppliedJobRepository  extends JpaRepository<AppliedJob, Long> {
    boolean existsByStudentAndJobPosting(Student student, JobPosting jobPosting);

    // Alternative method using student email and job ID
    @Query("SELECT COUNT(aj) > 0 FROM AppliedJob aj WHERE aj.student.email = :studentEmail AND aj.jobPosting.id = :jobId")
    boolean existsByStudentEmailAndJobId(@Param("studentEmail") String studentEmail, @Param("jobId") Long jobId);

    List<AppliedJob> findByStudent(Student student);

    // Find all applications for a job posting
    List<AppliedJob> findByJobPosting(JobPosting jobPosting);

    // Find specific application by student and job
    Optional<AppliedJob> findByStudentAndJobPosting(Student student, JobPosting jobPosting);


}
