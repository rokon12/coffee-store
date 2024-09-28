package org.eclipse.jakarta.hello;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;

@Repository
public interface CoffeeRepository extends CrudRepository<Coffee, Long> {
}
