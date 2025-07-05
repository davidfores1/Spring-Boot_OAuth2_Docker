/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.customer.controller;

import com.paymentchain.customer.business.transactions.BussinesTransaction;
import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.respository.CustomerRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author sotobotero
 */
@RestController
@RequestMapping("/customer/v1")
public class CustomerRestController {

    @Autowired
    CustomerRepository customerRepository;
    
    @Autowired
    BussinesTransaction bussinesTransaction;

    @Value("${custom.activeprofileName:local}")
    private String profile;

    @GetMapping("check")
    public String check() {
        return "Hello your profile value is: " + profile;
    }

    @GetMapping()
    public ResponseEntity<?> list() {
        List<Customer> findAll = customerRepository.findAll();

        if (findAll.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(findAll);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable("id") long id, @RequestBody Customer input) {
        Optional<Customer> optionalcustomer = customerRepository.findById(id);
        if (optionalcustomer.isPresent()) {
            Customer newcustomer = optionalcustomer.get();
            newcustomer.setName(input.getName());
            newcustomer.setPhone(input.getPhone());
            Customer save = customerRepository.save(newcustomer);
            return new ResponseEntity<>(save, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Customer input) {
        input.getProducts().forEach(x -> x.setCustomer(input));
        Customer save = customerRepository.save(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        Optional<Customer> findById = customerRepository.findById(id);
        if (findById != null) {
            customerRepository.deleteById(id);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/full/{code}")
    public Customer getByCode(@RequestParam("code") String code) {
        return bussinesTransaction.getByCode(code);
    }

}
