package org.green.cafe.services;

import io.quarkus.narayana.jta.runtime.TransactionConfiguration;
import org.green.cafe.exceptions.ValidationException;
import org.green.cafe.models.Employee;
import org.green.cafe.models.JobPosition;
import org.green.cafe.models.LastEducation;
import org.green.cafe.models.User;
import org.green.cafe.models.dto.EmployeeRequest;
import org.green.cafe.models.dto.EmployeeResponse;
import org.green.cafe.util.DateUtil;
import org.green.cafe.util.FormatUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EmployeeService {
  @Inject
  EntityManager entityManager;

  public Response post(String userId, EmployeeRequest employeeRequest) throws ParseException {
    if (!FormatUtil.isEmail(employeeRequest.email) || !FormatUtil.isPhoneNumber(employeeRequest.mobilePhoneNumber) || !FormatUtil.isGender(employeeRequest.gender) || !FormatUtil.isAlphabet(employeeRequest.fullName) || !FormatUtil.isDateFormat(employeeRequest.dob) || !FormatUtil.isAlphabet(employeeRequest.pob)) {
      throw new ValidationException("BAD_REQUEST_VALIDATE");
    }

    if (employeeRequest.jobPositionId == null || employeeRequest.lastEducationId == null || employeeRequest.jobPositionId.isBlank() || employeeRequest.jobPositionId.isEmpty() || employeeRequest.lastEducationId.isEmpty() || employeeRequest.lastEducationId.isBlank()) {
      throw new ValidationException("BAD_REQUEST_NULL");
    }

    Optional<JobPosition> jobPositionId = JobPosition.findByIdOptional(employeeRequest.jobPositionId);
    Optional<LastEducation> lastEducationId = LastEducation.findByIdOptional(employeeRequest.lastEducationId);
    if (jobPositionId.isEmpty() || lastEducationId.isEmpty()) {
      throw new ValidationException("BAD_REQUEST_JOB_POSITION_ID_OR_LAST_EDUCATION_ID");
    }

    Optional<User> userOptional = User.findByIdOptional(userId);
    if (userOptional.isEmpty()) {
      throw new ValidationException("USER_NOT_FOUND_FROM_CREATE_EMPLOYEE");
    }

    employeePersist("", employeeRequest, jobPositionId.get(), lastEducationId.get(), userOptional.get());

    return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
  }

  public Response put(String userId, String employeeId, EmployeeRequest employeeRequest) throws ParseException {
    if (employeeId == null || employeeId.isBlank()) {
      throw new ValidationException("BAD_REQUEST_NULL_BLANK");
    }

    if (!FormatUtil.isEmail(employeeRequest.email) || !FormatUtil.isPhoneNumber(employeeRequest.mobilePhoneNumber) || !FormatUtil.isGender(employeeRequest.gender) || !FormatUtil.isAlphabet(employeeRequest.fullName) || !FormatUtil.isDateFormat(employeeRequest.dob) || !FormatUtil.isAlphabet(employeeRequest.pob)) {
      throw new ValidationException("BAD_REQUEST_VALIDATE");
    }

    if (employeeRequest.jobPositionId == null || employeeRequest.lastEducationId == null || employeeRequest.jobPositionId.isBlank() || employeeRequest.jobPositionId.isEmpty() || employeeRequest.lastEducationId.isEmpty() || employeeRequest.lastEducationId.isBlank()) {
      throw new ValidationException("BAD_REQUEST_NULL");
    }

    Optional<JobPosition> jobPositionId = JobPosition.findByIdOptional(employeeRequest.jobPositionId);
    Optional<LastEducation> lastEducationId = LastEducation.findByIdOptional(employeeRequest.lastEducationId);
    if (jobPositionId.isEmpty() || lastEducationId.isEmpty()) {
      throw new ValidationException("BAD_REQUEST_JOB_POSITION_ID_OR_LAST_EDUCATION_ID");
    }

    Optional<User> userOptional = User.findByIdOptional(userId);
    if (userOptional.isEmpty()) {
      throw new ValidationException("USER_NOT_FOUND_FROM_UPDATE_EMPLOYEE");
    }

    employeePersist(employeeId, employeeRequest, jobPositionId.get(), lastEducationId.get(), userOptional.get());

    return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
  }

  @Transactional
  @TransactionConfiguration(timeout = 30)
  public Employee employeePersist(String employeeId, EmployeeRequest employeeRequest, JobPosition jobPosition, LastEducation lastEducation, User user) throws ParseException {
    Optional<Employee> employeeOptional = Employee.findByIdOptional(employeeId);
    Employee employee;
    if (employeeOptional.isEmpty()) {
      employee = new Employee();
      employee.setCreatedBy(user);
      employee.setIsActive(true);
      employee.setUpdatedBy(user);
    } else {
      employee = employeeOptional.get();
      employee.setUpdatedBy(user);
    }

    employee.setFullName(employeeRequest.fullName);
    employee.setGender(employeeRequest.gender);
    employee.setEmail(employeeRequest.email);
    employee.setMobilePhoneNumber(employeeRequest.mobilePhoneNumber);
    employee.setJobPosition(jobPosition);
    employee.setLastEducation(lastEducation);
    employee.setDob(DateUtil.stringToDate(employeeRequest.dob));
    employee.setPob(employeeRequest.pob);
    employee.persist();

    if (!employee.isPersistent()){
      throw new ValidationException("INVALID_UPDATE");
    }
    return employee;
  }

  public Response delete(String userId, String employeeUUID) {
    deleteEmployee(userId,employeeUUID);
    return Response.ok(new HashMap<>()).build();
  }

  @Transactional
  @TransactionConfiguration(timeout = 30)
  public void deleteEmployee(String userId, String employeeUUID) {
    Optional<User> userOptional = User.findByIdOptional(userId);
    if (userOptional.isEmpty()) {
      throw new ValidationException("USER_NOT_FOUND_FROM_DELETE_EMPLOYEE");
    }
    Optional<Employee> employeeId = Employee.findByIdOptional(employeeUUID);
    if (employeeId.isEmpty()) {
      throw new ValidationException("EMPLOYEE_NOT_FOUND");
    }
    employeeId.get().delete();
  }

  public Response list(Integer page, String jobPositionId) {
    if (page < 1) {
      throw new ValidationException("BAD_REQUEST");
    }

    Long count = Employee.countByJobPosition(jobPositionId);
    Double totalPage = Math.ceil(count/10.0);

    StringBuilder stringBuilder = new StringBuilder("SELECT * FROM main.employee\t");

    Integer offset = ((page-1)*10);
    if (jobPositionId != null) {
      stringBuilder.append("WHERE job_position_id = :id");
    }
    Query query = entityManager.createNativeQuery(stringBuilder.toString(), Employee.class);
    if (jobPositionId != null) {
      query.setParameter("id", jobPositionId);
    }
    query.setMaxResults(10);
    query.setFirstResult(offset);

    List<Employee> list = query.getResultList();

    EmployeeResponse employeeResponse = new EmployeeResponse();
    employeeResponse.totalPage = totalPage.longValue();
    employeeResponse.dataList = list;

    return Response.ok(employeeResponse).build();
  }

  public Response detail(String employeeId) {
    Employee employeeOptional = Employee.findById(employeeId);
    if (employeeOptional == null) {
      throw new ValidationException("BAD_REQUEST");
    }
    return Response.ok(employeeOptional.list("id = ?1", employeeId)).build();
  }

}


//  public Response getById(String uuid){
//    List<Employee> employeeId = Employee.findByUUID(uuid);
//    return employeeId.isEmpty() ?
//        Response.status(Response.Status.NOT_FOUND).build() :
//        Response.ok(employeeId).build();
//  }
//
//}

//  public Response get(Integer page, String jobPosition){
//    if(page < 1){
//      throw new ValidationException("BAD_REQUEST");
//    }
//
//    Long count = Employee.countByJobPosition(jobPosition);
//    Double totalPage = Math.ceil(count/10);
//
//    Optional<Employee> list = Employee.findByJobPosition(jobPosition, page);
//    EmployeeResponse employeeResponse = new EmployeeResponse();
//    employeeResponse.totalPage = totalPage.longValue();
//    employeeResponse.dataList = list;
//    return Response.status(Response.Status.FOUND)
//        .entity(entityManager
//            .createNativeQuery(
//                "SELECT * FROM employee WHERE job_position_id = " + jobPosition + " LIMIT " + page
//            )
//            .getResultList()).build();
//    return Response.ok(employeeResponse).build();
//  }
//}
