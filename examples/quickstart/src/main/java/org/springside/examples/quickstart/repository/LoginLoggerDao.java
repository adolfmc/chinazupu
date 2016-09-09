package org.springside.examples.quickstart.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springside.examples.quickstart.entity.LoginLogger;

public interface LoginLoggerDao extends PagingAndSortingRepository<LoginLogger, Long>, JpaSpecificationExecutor<LoginLogger> {

}
