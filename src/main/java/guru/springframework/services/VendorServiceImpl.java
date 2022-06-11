package guru.springframework.services;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.domain.VendorPagedList;
import guru.springframework.exceptions.ResourceNotFoundException;
import guru.springframework.repositories.VendorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class VendorServiceImpl implements VendorService {

    private final VendorMapper mapper;
    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper mapper, VendorRepository vendorRepository) {
        this.mapper = mapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public VendorPagedList getAllVendorsByPage(Pageable pageable) {
        Page<Vendor> vendorPage = vendorRepository.findAll(pageable);

        return new VendorPagedList(vendorPage
                .stream()
                .map(mapper::vendorToVendorDTO)
                .collect(Collectors.toList()),
                PageRequest.of(vendorPage.getPageable().getPageNumber(),
                vendorPage.getPageable().getPageSize()),
                vendorPage.getTotalElements());
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(mapper::vendorToVendorDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(mapper::vendorToVendorDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor vendor = mapper.venderDTOToVendor(vendorDTO);
        Vendor savedVendor = vendorRepository.save(vendor);
        return mapper.vendorToVendorDTO(savedVendor);
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor = mapper.venderDTOToVendor(vendorDTO);
        vendor.setId(id);
        Vendor savedVendor = vendorRepository.save(vendor);
        return mapper.vendorToVendorDTO(savedVendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return null;
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }
}
