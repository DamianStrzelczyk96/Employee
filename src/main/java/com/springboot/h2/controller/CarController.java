package com.springboot.h2.controller;

import com.springboot.h2.model.Car;
import com.springboot.h2.model.Employee;
import com.springboot.h2.service.CarService;
import com.springboot.h2.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final EmployeeService employeeService;

    private Set<Car> carList = new HashSet<>();

    @PostMapping(value = "/save_car")
    public String saveCar(Model model, @ModelAttribute(value = "car") @Valid  Car car, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("empList", employeeService.getAll());

            return "car/car_form";
        }
        if (car.getId() == 0) {
            carService.save(car);
            getCarList().add(car);
        } else {
            Car carTemp = carService.edit(car.getId());
            carTemp.setName(car.getName());
            carTemp.setModel(car.getModel());
            carTemp.setRegistrationDate(car.getRegistrationDate());
            carTemp.setEmployees(car.getEmployees());
            carService.save(car);
        }

        return ("redirect:/car_list");
    }
    @RequestMapping(value = "/car_form", method = RequestMethod.GET)
    public String showform(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("empList", employeeService.getAll());
        return "car/car_form";
    }

    @RequestMapping("/car_list")
    public ModelAndView car_list() {
        return new ModelAndView("car/car_list", "list", getCarList());
    }
    @PostMapping(value = "/delete_car")
    public ModelAndView deleteCar(@RequestParam(value = "car_id") int car_id) {
        Car car = carService.edit(car_id);
        carService.delete(car);
        carList.remove(car);
        return new ModelAndView("redirect:/car_list");
    }
    @RequestMapping(value = "/edit_car", method = RequestMethod.POST)
    public ModelAndView editCar(@RequestParam(value = "car_id") int car_id) {
        Car car = carService.edit(car_id);
        return new ModelAndView("car/car_edit", "car", car);
    }
    private Car getCarById(int id) {
        return carList.stream().filter(f -> f.getId() == id).findFirst().get();
    }

    public Set<Car> getCarList() {
        return carService.getAll();
    }

}
