package org.springside.examples.quickstart.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springside.examples.quickstart.entity.Clan;

public interface ClanDao extends PagingAndSortingRepository<Clan, Long>, JpaSpecificationExecutor<Clan> {

}
