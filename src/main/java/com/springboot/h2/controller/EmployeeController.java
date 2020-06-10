package com.springboot.h2.controller;

import com.springboot.h2.model.Car;
import com.springboot.h2.model.Employee;
import com.springboot.h2.service.EmployeeService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.internal.Cascade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
//    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAService");
//    static EntityManager em = emf.createEntityManager();

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final EmployeeService employeeService;

    private Set<Employee> employeeList = new HashSet<>();



    @GetMapping("/")
    public String index() {
        return "employee/index";
    }

    @RequestMapping(value = "/employee_form", method = RequestMethod.GET)
    public String showform(Model model) {
        Date nowDate = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("localDate",sdf1.format(nowDate));
        model.addAttribute("employee", new Employee());
        return "employee/employee_form";
    }

    @PostMapping(value = "/addCar")
    public String addCar(Model model, @RequestParam(value = "emp_id") int emp_id) {
        Employee employee = employeeService.edit(emp_id);
        Car car = new Car();
        car.setEmployees(employee);
        model.addAttribute("car", car);
        model.addAttribute("empList", employeeService.getAll());
        return "car/car_form";
    }

    @PostMapping(value = "/save")
    public String save(Model model,@ModelAttribute @Valid Employee employee, BindingResult result) {
        Date nowDate = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("localDate",sdf1.format(nowDate));
        System.out.println(sdf1.format(nowDate));
        if (result.hasErrors()) {
            return "employee/employee_form";
        }

        if (employee.getId() == 0) {
            employeeService.save(employee);
            getEmployeeList().add(employee);
        } else {
            Employee empTemp = employeeService.edit(employee.getId());
            empTemp.setFirstName(employee.getFirstName());
            empTemp.setSalary(employee.getSalary());
            empTemp.setAddress(employee.getAddress());
            empTemp.setBenefit(employee.getBenefit());
            empTemp.setAge(employee.getAge());
            empTemp.setCity(employee.getCity());
            empTemp.setLastName(employee.getLastName());
            empTemp.setStartJobDate(employee.getStartJobDate());
            empTemp.setEmail(employee.getEmail());
            employeeService.save(employee);
        }

        return "redirect:/employee_list";
    }

    @PostMapping(value = "/delete")
    public ModelAndView delete(@RequestParam(value = "emp_id") int emp_id) {
        Employee employee = employeeService.edit(emp_id);
        employeeService.delete(employee);
        employeeList.remove(employee);
        return new ModelAndView("redirect:/employee_list");
    }

    @PostMapping(value = "/edit")
    public ModelAndView edit(@RequestParam(value = "emp_id") int emp_id) {
        Employee employee = employeeService.edit(emp_id);
        return new ModelAndView("employee/employee_form", "employee", employee);
    }

    @RequestMapping("/employee_list")
    public ModelAndView employee_list() {
        return new ModelAndView("employee/employee_list", "list", getEmployeeList());
    }

    private Employee getEmployeeById(int id) {
        return employeeList.stream().filter(f -> f.getId() == id).findFirst().get();
    }

    public Set<Employee> getEmployeeList() {
        return employeeService.getAll();
    }

}