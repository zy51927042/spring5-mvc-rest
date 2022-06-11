package guru.springframework.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.springframework.api.v1.model.VendorDTO;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class VendorPagedList extends PageImpl<VendorDTO> {

    public VendorPagedList(@JsonProperty("vendors")List<VendorDTO> content,
                           Pageable pageable,
                           long total) {
        super(content, pageable, total);
    }

    public VendorPagedList(List<VendorDTO> content) {
        super(content);
    }
}
