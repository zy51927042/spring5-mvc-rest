package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CustomerServiceTest {

    public static final long ID = 1L;
    public static final String FIRST_NAME = "Mickel";
    public static final String LAST_NAME = "Jackson";
    CustomerService customerService;
    @Mock
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(customerRepository,CustomerMapper.INSTANCE);
    }

    @Test
    void getAllCustomers() {
        //given
        List<Customer> customers = Arrays.asList(new Customer(),new Customer());
        when(customerRepository.findAll()).thenReturn(customers);
        //when
        List<CustomerDTO> customerDTOS= customerService.getAllCustomers();
        //then
        assertEquals(customerDTOS.size(),2);
    }

    @Test
    void getCustomerById() {
        //given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setLastName(LAST_NAME);
        customer.setFirstName(FIRST_NAME);
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        //when
        CustomerDTO customerDTO = customerService.getCustomerById(ID);
        //then

        assertEquals(FIRST_NAME,customerDTO.getFirstname());
        assertEquals(LAST_NAME,customerDTO.getLastname());
    }
}