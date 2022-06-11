package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.VendorPagedList;
import guru.springframework.services.VendorService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
    public static final String BASE_URL = "/api/v1/vendors";
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final VendorService vendorService;


    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorPagedList getAllVendors(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize){
        if (pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return vendorService.getAllVendorsByPage(PageRequest.of(pageNumber,pageSize));

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable Long id) {
        return vendorService.getVendorById(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createNewVendor(vendorDTO);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO saveVendorById(@PathVariable Long id, @RequestBody VendorDTO vendorDTO){
        return vendorService.saveVendorByDTO(id,vendorDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendorById(id);
    }

}
