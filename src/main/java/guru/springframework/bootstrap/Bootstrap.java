package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
        loadVendors();
    }

    private void loadVendors() {
        Vendor beer1 = new Vendor();
        beer1.setName("Beer Factory1");
        Vendor beer2 = new Vendor();
        beer2.setName("Beer Factory2");
        Vendor beer3 = new Vendor();
        beer3.setName("Beer Factory3");
        Vendor beer4 = new Vendor();
        beer4.setName("Beer Factory4");
        Vendor beer5 = new Vendor();
        beer5.setName("Beer Factory5");
        Vendor beer6 = new Vendor();
        beer6.setName("Beer Factory6");

        vendorRepository.save(beer1);
        vendorRepository.save(beer2);
        vendorRepository.save(beer3);
        vendorRepository.save(beer4);
        vendorRepository.save(beer5);
        vendorRepository.save(beer6);


    }
    private void loadCustomers() {
        Customer jack = new Customer();
        jack.setFirstName("Jack");
        jack.setLastName("Ma");
        Customer sam = new Customer();
        sam.setFirstName("Sam");
        sam.setLastName("Smith");

        customerRepository.save(jack);
        customerRepository.save(sam);
        System.out.println("Customers loaded: " + customerRepository.count());
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");
        Category dried = new Category();
        dried.setName("Dried");
        Category fresh = new Category();
        fresh.setName("Fresh");
        Category exotic = new Category();
        exotic.setName("Exotic");
        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Data Loaded = " +categoryRepository.count());
    }
}
