package org.springside.examples.quickstart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springside.examples.quickstart.entity.WifeAHusBd;

public interface WifeAHusBdDao extends PagingAndSortingRepository<WifeAHusBd, Long>, JpaSpecificationExecutor<WifeAHusBd> {

	List<WifeAHusBd> findByWifeId(Long wifeId);

	List<WifeAHusBd> findByHusbandId(Long husbandId);

	@Modifying
	@Query(value = "delete czp_t_wifehud wh where wh.husband_id  in ( select t.id from ss_task t left join ss_task t2 on t.clan_id = t2.clan_id and t.code like t2.code||'%' and t2.id =?1 )" ,nativeQuery=true)
	void removeH(Long id);

	@Modifying
	@Query(value = "delete czp_t_wifehud wh where wh.wife_id  in ( select t.id from ss_task t left join ss_task t2 on t.clan_id = t2.clan_id and t.code like t2.code||'%' and t2.id =?1 )" ,nativeQuery=true)
	void removew(Long id);
}

