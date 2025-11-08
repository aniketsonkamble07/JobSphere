package com.aniket.placementcell.service;

import com.aniket.placementcell.dto.*;
import com.aniket.placementcell.entity.AppliedJob;
import com.aniket.placementcell.entity.JobPosting;
import com.aniket.placementcell.entity.Student;
import com.aniket.placementcell.enums.ApplicationStatus;
import com.aniket.placementcell.enums.JobStatus;
import com.aniket.placementcell.exceptions.JobIdNotFoundException;
import com.aniket.placementcell.repository.AppliedJobRepository;
import com.aniket.placementcell.repository.JobPostRepository;
import com.aniket.placementcell.repository.StudentRepository;
import com.aniket.placementcell.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private AppliedJobRepository appliedJobRepository;

    public StudentResponseDTO sendProfile(String name)
    {
        StudentResponseDTO dto=new StudentResponseDTO();

        if(userRepository.existsByUsername(name))
        {
            Student s=studentRepository.findByEmail(name).orElseThrow(()->new UsernameNotFoundException(name+" Not found!!"));
            dto.setName(s.getName());
            dto.setCrnNumber(s.getCrnNumber());
            dto.setEmail(s.getEmail());
            dto.setBranch(s.getBranch());
            dto.setStudentYear(s.getYear());
            dto.setActiveBacklog(s.getActiveBacklog());
            dto.setMobileNumber(s.getMobileNumber());
            dto.setGender(s.getGender());

            dto.setCgpa(s.getCgpa());
            dto.setMark10th(s.getMark10th());
            dto.setMark12th(s.getMark12th());
            dto.setDiplomaMarks(s.getDiplomaMarks());

            dto.setPlacementStatus(s.getPlacementStatus());
            dto.setCompanyName(s.getCompanyName());
            dto.setAppliedDTOList(
                    s.getAppliedList().stream()
                            .map(applied -> {
                                JobPosting job = applied.getJobPosting();
                                AppliedDTO appliedDTO = new AppliedDTO();
                                appliedDTO.setJobId(job.getId());
                                appliedDTO.setTitle(job.getTitle());
                                appliedDTO.setCompanyName(job.getCompanyName());
                                appliedDTO.setLocation(job.getLocation());
                                appliedDTO.setStatus(job.getStatus());
                                appliedDTO.setAppliedDate(applied.getAppliedDate());
                                appliedDTO.setApplicationStatus(applied.getStatus().toString());
                                return appliedDTO;
                            })
                            .toList()
            );

        }


        return dto;
    }

    public JobPostingResponseDTO getJobPosting(String jobId) {
        try {
            // Convert String jobId to Long (assuming jobId is the entity ID)
            Long id = Long.parseLong(jobId);
            JobPosting job = jobPostRepository.findById(id)
                    .orElseThrow(() -> new JobIdNotFoundException("Job with ID " + jobId + " not found!!"));

            return mapToJobPostingResponseDTO(job);

        } catch (NumberFormatException e) {
            throw new JobIdNotFoundException("Invalid job ID format: " + jobId);
        }
    }

    public List<JobPostingResponseDTO> getAllJobPosting() {
        List<JobPosting> jobPostings = jobPostRepository.findByStatusOrderByCreatedAtDesc(JobStatus.PUBLISHED);

        return jobPostings.stream()
                .map(this::mapToJobPostingResponseDTO)
                .toList();
    }



    private JobPostingResponseDTO mapToJobPostingResponseDTO(JobPosting job) {
        JobPostingResponseDTO dto = new JobPostingResponseDTO();

        // Use job.getId() for jobId since that's what you're using in getAllJobPosting
        dto.setJobId(job.getId().toString());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setJobType(job.getJobType());
        dto.setEmploymentType(job.getEmploymentType());
        dto.setLocation(job.getLocation());
        dto.setMinSalary(job.getMinSalary());
        dto.setMaxSalary(job.getMaxSalary());
        dto.setSalaryType(job.getSalaryType());
        dto.setRequiredCGPA(job.getRequiredCGPA());
        dto.setRequiredSkills(job.getRequiredSkills());
        dto.setRequiredBranches(job.getRequiredBranches());
        dto.setApplicationDeadline(job.getApplicationDeadline());
        dto.setDriveDate(job.getDriveDate());
        dto.setDriveTime(job.getDriveTime());
        dto.setDriveVenue(job.getDriveVenue());
        dto.setNumberOfVacancies(job.getNumberOfVacancies());
        dto.setStatus(job.getStatus());
        dto.setCompanyName(job.getCompanyName());

        // Map placement officer info safely
        if (job.getPostedBy() != null) {
            dto.setPostedByName(job.getPostedBy().getName());
            dto.setPostedByEmail(job.getPostedBy().getEmail());
        }

        dto.setViewsCount(job.getViewsCount());
        dto.setApplicationsCount(job.getApplicationsCount());
        dto.setCreatedAt(job.getCreatedAt());
        dto.setUpdatedAt(job.getUpdatedAt());
        dto.setPublishedAt(job.getPublishedAt());

        return dto;
    }




    public StudentUpdateResponseDTO sendStudentForUpdate(String username) {
        Student student = studentRepository.findByEmail(username)
                .orElse(null); // return null if student not found

        if (student == null) {
            return null;
        }

        // Map entity to DTO
        StudentUpdateResponseDTO dto = new StudentUpdateResponseDTO();
        dto.setCrnNumber(student.getCrnNumber());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());

        dto.setBranch(student.getBranch());
        dto.setYear(student.getYear());
        dto.setPassingYear(student.getPassingYear());
        dto.setMobileNumber(student.getMobileNumber());
        dto.setCgpa(student.getCgpa());
        dto.setMark10th(student.getMark10th());
        dto.setMark12th(student.getMark12th());
        dto.setDiplomaMarks(student.getDiplomaMarks());
        dto.setAggregateMarks(student.getAggregateMarks());
        dto.setYearDown(student.getYearDown());
        dto.setActiveBacklog(student.getActiveBacklog());
        dto.setPlacementStatus(student.getPlacementStatus());
        dto.setRemarks(student.getRemarks());
        dto.setGender(student.getGender());
        dto.setCompanyName(student.getCompanyName());
        dto.setSalary(student.getSalary());
        return dto;
    }

    // Update student profile from DTO

    public void updateStudentProfile(StudentUpdateRequestDTO dto) {
        Student student = studentRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Update fields
        student.setCrnNumber(dto.getCrnNumber());
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());

        student.setBranch(dto.getBranch());
        student.setYear(dto.getYear());
        student.setPassingYear(dto.getPassingYear());
        student.setMobileNumber(dto.getMobileNumber());
        student.setCgpa(dto.getCgpa());
        student.setMark10th(dto.getMark10th());
        student.setMark12th(dto.getMark12th());
        student.setDiplomaMarks(dto.getDiplomaMarks());
        student.setAggregateMarks(dto.getAggregateMarks());
        student.setYearDown(dto.getYearDown());
        student.setActiveBacklog(dto.getActiveBacklog());
        student.setPlacementStatus(dto.getPlacementStatus());
        student.setRemarks(dto.getRemarks());
        student.setGender(dto.getGender());
        student.setCompanyName(dto.getCompanyName());
        student.setSalary(dto.getSalary());

        studentRepository.save(student); // persist changes
    }


    public String applyForJob(String jobId, String username)
    {
        Student s=studentRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Username : "+ username +" not found!!"));
        Long id = Long.parseLong(jobId);
        JobPosting jobPosting = jobPostRepository.findById(id)
                .orElseThrow(() -> new JobIdNotFoundException("Job with ID " + jobId + " not found!!"));

        if (jobPosting.getApplicationDeadline().isBefore(LocalDate.now())) {
            return "Deadline is completed";
        }

        if(jobPosting.getRequiredCGPA() >=s.getCgpa())
        {
            return "Your cga is less than required cgpa ";
        }
        if (!(jobPosting.getRequiredBranches().stream()
                .anyMatch(branch -> branch.equals(s.getBranch())))) {
            return "Your branch does not match with required branch";
        }
        boolean alreadyApplied = appliedJobRepository.existsByStudentAndJobPosting(s, jobPosting);
        if (alreadyApplied) {
            return "You have already applied for this job";
        }

        // Save applied job
        AppliedJob appliedJob = new AppliedJob(jobPosting, s, LocalDateTime.now(), ApplicationStatus.APPLIED);
        appliedJobRepository.save(appliedJob);

        return "Applied successfully!";
    }
    }



