package org.springside.examples.quickstart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springside.examples.quickstart.entity.WifeAHusBd;

public interface WifeAHusBdDao extends PagingAndSortingRepository<WifeAHusBd, Long>, JpaSpecificationExecutor<WifeAHusBd> {

	List<WifeAHusBd> findByWifeId(Long wifeId);

	List<WifeAHusBd> findByHusbandId(Long husbandId);
}

