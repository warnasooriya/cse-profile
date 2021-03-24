expackage com.stock.soft.socksoft.repository;

import com.stock.soft.socksoft.model.Commission;
import org.springframework.data.repository.CrudRepository;

public interface CommissionRepository extends CrudRepository<Commission, String> {
}
