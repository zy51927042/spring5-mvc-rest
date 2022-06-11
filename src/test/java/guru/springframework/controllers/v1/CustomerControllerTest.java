package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerListDTO;
import guru.springframework.controllers.RestResponseEntityExceptionHandler;
import guru.springframework.services.CustomerService;
import guru.springframework.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static guru.springframework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class CustomerControllerTest {

    private static final String URI = "/api/v1/customers/";
    private static final Long ID = 1L;
    private static final String FIRST_NAME = "Mickel";
    private static final String LAST_NAME = "Jackson";
    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController controller;

    MockMvc mockMvc;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }
    @Test
    void getAllCustomers() throws Exception{
        //given
        List<CustomerDTO> customerDTOs = Arrays.asList(new CustomerDTO(),new CustomerDTO());
        CustomerListDTO customerListDTO = new CustomerListDTO(customerDTOs);

        when(customerService.getAllCustomers()).thenReturn(customerDTOs);
        //when/then
        mockMvc.perform(MockMvcRequestBuilders.get(URI).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers",hasSize(2)));
    }

    @Test
    void getCustomerById() throws Exception{
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setFirstname(FIRST_NAME);
        customerDTO.setLastname(LAST_NAME);

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(URI+ID).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname",equalTo(FIRST_NAME)));
    }

    @Test
    void getCustomerNotFound() throws Exception {
        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(URI+ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createNewCustomer()throws Exception{
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setLastname(LAST_NAME);
        customer.setFirstname(FIRST_NAME);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customer.getFirstname());
        returnDTO.setLastname(customer.getLastname());
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname",equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.customer_url",equalTo("/api/v1/customers/1")));
    }

    @Test
    public void updateCustomer() throws Exception{
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRST_NAME);
        customerDTO.setLastname(LAST_NAME);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customerDTO.getFirstname());
        returnDTO.setLastname(customerDTO.getLastname());
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.saveCustomerByDTO(anyLong(),any(CustomerDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname",equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastname",equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.customer_url",equalTo("/api/v1/customers/1")));
    }

    @Test
    public void patchCustomer() throws Exception{
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Fred");


        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customerDTO.getFirstname());
        returnDTO.setLastname("Flinstone");
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.patchCustomer(anyLong(),any(CustomerDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname",equalTo("Fred")))
                .andExpect(jsonPath("$.lastname",equalTo("Flinstone")))
                .andExpect(jsonPath("$.customer_url",equalTo("/api/v1/customers/1")));
    }
    @Test
    public void deleteCustomer() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(customerService).deleteCustomerById(anyLong());

    }
}