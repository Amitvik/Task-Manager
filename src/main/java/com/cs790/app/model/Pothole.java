package com.cs790.app.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Pothole")
public class Pothole {

       @Id
       private String id;
       private String streetName;
       private String dateUploaded;

       private Map<String,String> imageMetaData;

       private String userId;

}
