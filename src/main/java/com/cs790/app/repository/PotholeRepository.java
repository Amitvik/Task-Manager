package com.cs790.app.repository;

import com.cs790.app.model.Pothole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


//@RepositoryRestResource(collectionResourceRel = "pothole", path = "pothole")
//@RepositoryRestResource
public interface PotholeRepository extends MongoRepository<Pothole,String> {

}
