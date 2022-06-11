package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.controllers.RestResponseEntityExceptionHandler;
import guru.springframework.domain.Vendor;
import guru.springframework.domain.VendorPagedList;
import guru.springframework.exceptions.ResourceNotFoundException;
import guru.springframework.repositories.VendorRepository;
import guru.springframework.services.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.print.attribute.standard.MediaSize;
import java.util.Arrays;
import java.util.List;

import static guru.springframework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VendorControllerTest {

    public static final String NAME = "Jack F";
    @Mock
    VendorService vendorService;
    @InjectMocks
    VendorController controller;

    MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    void getVendorById() throws Exception{
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);
        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO);
        //when /then
        mockMvc.perform(MockMvcRequestBuilders.get(VendorController.BASE_URL+"/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(NAME)));
    }
    void getVendorNotFound() throws Exception {
        //given
        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);
        //when/then
        mockMvc.perform(MockMvcRequestBuilders.get(VendorController.BASE_URL+"/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    void getAllVendors() throws Exception{
        //given
        List<VendorDTO> vendors = Arrays.asList(new VendorDTO(),new VendorDTO());
        VendorPagedList vendorPagedList = new VendorPagedList(vendors, PageRequest.of(0,25),2);
        when(vendorService.getAllVendorsByPage( PageRequest.of(0,25))).thenReturn(vendorPagedList);


    /*   MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();*/

        //when /then
        mockMvc.perform(MockMvcRequestBuilders.get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",hasSize(2)));

    }

    @Test
    void createNewVendor() throws Exception{
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);
        when(vendorService.createNewVendor(any(VendorDTO.class))).thenReturn(vendorDTO);
        //when /then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/vendors/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",equalTo(NAME)));
    }
    @Test
    void saveVendorById() throws Exception {
      //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);
        when(vendorService.saveVendorByDTO(anyLong(),any(VendorDTO.class))).thenReturn(vendorDTO);
        //when /then
        mockMvc.perform(MockMvcRequestBuilders.put(VendorController.BASE_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(NAME)));
    }

    @Test
    void deleteVendor() throws  Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(VendorController.BASE_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(vendorService,times(1)).deleteVendorById(anyLong());

    }

}