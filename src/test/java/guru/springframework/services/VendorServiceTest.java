package guru.springframework.services;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

class VendorServiceTest {

    public static final long ID = 1L;
    public static final String NAME = "Beer Factory";
    VendorService vendorService;
    @Mock
    VendorRepository vendorRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE,vendorRepository);
    }

    @Test
    void getAllVendorsByPage() {
    }

    @Test
    void getAllVendors() {
        //given
        List<Vendor> vendors = Arrays.asList(new Vendor(),new Vendor());
        when(vendorRepository.findAll()).thenReturn(vendors);
        //when
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();
        //then
        assertEquals(vendorDTOS.size(),2);
    }
    @Test
    void getVendorByIdBDD() {
        //mockito BDD syntax
        Vendor vendor = getVendor1();
        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(1L);

        //then
        then(vendorRepository).should(times(1)).findById(anyLong());

        //JUnit Assert that with matchers
        assertThat(vendorDTO.getName(), is(equalTo(NAME)));
    }
    @Test
    void getVendorById() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
        //when
        VendorDTO vendorDTO = vendorService.getVendorById(ID);
        //then
        assertEquals(vendorDTO.getName(),NAME);
        verify(vendorRepository,times(1)).findById(anyLong());

    }

    @Test
    void createNewVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);
        Vendor savedVendor = new Vendor();
        savedVendor.setId(ID);
        savedVendor.setName(vendorDTO.getName());
        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);
        //when
        VendorDTO savedVendorDTO = vendorService.createNewVendor(vendorDTO);
        //then
        assertEquals(savedVendorDTO.getName(),vendorDTO.getName());
    }

    @Test
    void saveVendorByDTO() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);
        Vendor savedVendor = new Vendor();
        savedVendor.setId(ID);
        savedVendor.setName(NAME);
        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);
        //when
        VendorDTO savedVendorDTO = vendorService.saveVendorByDTO(ID,vendorDTO);
        //then
        assertEquals(savedVendorDTO.getName(),vendorDTO.getName());

    }

    @Test
    void patchVendor() {
    }

    @Test
    void deleteVendorById() {
        vendorService.deleteVendorById(ID);
        verify(vendorRepository,times(1)).deleteById(anyLong());
    }
    private Vendor getVendor1() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);
        return vendor;
    }

    private Vendor getVendor2() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);
        return vendor;
    }

}