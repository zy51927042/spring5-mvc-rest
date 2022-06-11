package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.bootstrap.Bootstrap;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class})
@DataJpaTest
public class CustomerServiceImplIT {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    VendorRepository vendorRepository;

    CustomerService customerService;
    @BeforeEach
    void setUp() throws Exception {
        Bootstrap bootstrap = new Bootstrap(categoryRepository,customerRepository, vendorRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }
    @Test
    void patchCustomerUpdateFirstName() throws Exception {
        String updatedName = "UpdatedName";
        Long id = getCustomerIdValue();

        Customer originalCust = customerRepository.findById(id).orElse(null);
        assertNotNull(originalCust);
        String originalFirstName = originalCust.getFirstName();
        String originalLastName = originalCust.getLastName();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(updatedName);

        customerService.patchCustomer(id,customerDTO);
        Customer updatedCust = customerRepository.findById(id).orElse(null);
        assertNotNull(updatedCust);
        assertEquals(updatedName, updatedCust.getFirstName());
        assertNotEquals(originalFirstName,updatedCust.getFirstName());
        assertEquals(originalLastName,updatedCust.getLastName());

    }

    private Long getCustomerIdValue(){
        List<Customer> customers = customerRepository.findAll();
        System.out.println("Customers Size" + customers.size());
        return customers.get(0).getId();
    }
    @Test
    void testPage() throws Exception{
        Page<Customer> page =  customerRepository.findAll(PageRequest.of(1,1));
        System.out.println(page);

    }
}
