package com.pocketbazaar.api.repository;


import com.pocketbazaar.api.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {

    List<Address> findByUserId(String userId); // Fetch all addresses for a user
}

