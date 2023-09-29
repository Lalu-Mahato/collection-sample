package com.example.collectionsample.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.collectionsample.collection.dto.TargetCollectionDTO;

import lombok.Data;

@Service
public class CollectionService {

    public ResponseEntity<Object> findCollectionTarget(String date) {
        List<collectionList> collectionList = new ArrayList<>();

        collectionList.add(new collectionList(25000.00, 25, 345, "2023-04-12"));
        collectionList.add(new collectionList(18000.50, 18, 220, "2023-07-28"));
        collectionList.add(new collectionList(35000.75, 30, 480, "2023-02-15"));
        collectionList.add(new collectionList(12000.80, 12, 150, "2023-09-05"));
        collectionList.add(new collectionList(42000.25, 40, 600, "2023-11-20"));
        collectionList.add(new collectionList(28000.60, 28, 380, "2023-06-08"));
        collectionList.add(new collectionList(15000.45, 15, 280, "2023-03-17"));
        collectionList.add(new collectionList(32000.90, 32, 550, "2023-08-02"));
        collectionList.add(new collectionList(20000.30, 20, 400, "2023-05-10"));
        collectionList.add(new collectionList(40000.70, 35, 700, "2023-10-12"));

        var target = collectionList.stream()
                .filter(myData -> myData.getDate().equals(date))
                .collect(Collectors.toList());

        if (target.size() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
        }
        TargetCollectionDTO response = new TargetCollectionDTO();
        response.setDate(target.get(0).getDate());
        response.setPendigCollections(target.get(0).getPendingCollections());
        response.setPendingGroups(target.get(0).getPendingGroups());
        response.setPendingCustomers(target.get(0).getPendingCustomers());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}

@Data
class collectionList {
    private Double pendingCollections;
    private Integer pendingGroups;
    private Integer pendingCustomers;
    private String date;

    collectionList(Double pendingCollections, Integer pendingGroups, Integer pendingCustomers, String date) {
        this.pendingCollections = pendingCollections;
        this.pendingGroups = pendingGroups;
        this.pendingCustomers = pendingCustomers;
        this.date = date;
    }
}
