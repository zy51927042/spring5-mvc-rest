package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VendorMapperTest {
    public static final String NAME = "Fruit Company";
    VendorMapper mapper = VendorMapper.INSTANCE;

    @Test
    void vendorToVendorDTO() {
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName(NAME);
        VendorDTO vendorDTO =mapper.vendorToVendorDTO(vendor);
        assertEquals(NAME,vendorDTO.getName());
    }
}