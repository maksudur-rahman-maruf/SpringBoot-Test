package com.example.SpringBootTestServiceLayer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void initUseCase() {
        List<Employee> customers = Arrays.asList(
                new Employee(1, "mad", "max", "mad.max@gmail.com")
        );
        employeeRepository.saveAll(customers);
    }

    @AfterEach
    public void destroyAll(){
        employeeRepository.deleteAll();
    }

    @Test
    void saveAll_success() {
        List<Employee> customers = Arrays.asList(
                new Employee(2,"sajedul", "karim", "karim.max@gmail.com"),
                new Employee(3,"nafis", "khan", "khan.max@gmail.com"),
                new Employee(4,"aayan", "karim", "aayan.max@gmail.com")
        );
        Iterable<Employee> allEmployee = employeeRepository.saveAll(customers);

        AtomicInteger validIdFound = new AtomicInteger();
        allEmployee.forEach(customer -> {
            if(customer.getId()>0){
                validIdFound.getAndIncrement();
            }
        });

        assertThat(validIdFound.intValue()).isEqualTo(3);
    }

    @Test
    void findAll_success() {
        List<Employee> allEmployee = employeeRepository.findAll();
        assertThat(allEmployee.size()).isGreaterThanOrEqualTo(1);
    }
    
}
