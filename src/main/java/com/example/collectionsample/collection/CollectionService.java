package com.example.collectionsample.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.collectionsample.collection.dto.TargetCollectionDTO;
import com.example.collectionsample.collection.entity.Collection;
import com.example.collectionsample.group.GroupRepository;
import com.example.collectionsample.group.entity.Group;
import com.example.collectionsample.prospect.ProspectRepository;
import com.example.collectionsample.prospect.entity.Prospect;

import lombok.Data;

@Service
public class CollectionService {
    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ProspectRepository prospectRepository;

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

    public ResponseEntity<Object> uploadCollections(MultipartFile multipartFile) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet worksheet = workbook.getSheet("Sheet1");

            List<Collection> collections = new ArrayList<>();
            List<Group> groups = new ArrayList<>();
            List<Prospect> prospects = new ArrayList<>();

            for (var i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = worksheet.getRow(i);

                // Prepare collection data
                Collection collection = new Collection();
                collection.setProspectId((String) row.getCell(0).getStringCellValue());
                collection.setGroupId((String) row.getCell(2).getStringCellValue());
                collection.setProductType((String) row.getCell(4).getStringCellValue());
                collection.setLoanAccNumber((Long) Math.round(row.getCell(7).getNumericCellValue()));
                collection.setLoanAmount((double) Math.round(row.getCell(13).getNumericCellValue()));
                collection.setEmiAmount((double) Math.round(row.getCell(14).getNumericCellValue()));
                collection.setIntEmiAmount((double) Math.round(row.getCell(14).getNumericCellValue()));
                collection.setDpd((double) Math.round(row.getCell(15).getNumericCellValue()));
                collection.setDpdAmount((double) Math.round(row.getCell(16).getNumericCellValue()));
                collection.setOsBalance((double) Math.round(row.getCell(17).getNumericCellValue()));
                collection.setFoId((String) row.getCell(19).getStringCellValue());
                collection.setBmId((String) row.getCell(21).getStringCellValue());
                collection.setEmiStatus((String) row.getCell(26).getStringCellValue());
                collection.setEmiCollected((double) Math.round(row.getCell(27).getNumericCellValue()));
                collections.add(collection);

                // Prepare group data
                Group group = new Group();
                group.setGroupId((String) row.getCell(2).getStringCellValue());
                group.setGroupName((String) row.getCell(3).getStringCellValue());
                group.setProductType((String) row.getCell(4).getStringCellValue());
                group.setCentreId((String) row.getCell(9).getStringCellValue());
                group.setLocaleName((String) row.getCell(10).getStringCellValue());
                group.setLocalId((String) row.getCell(11).getStringCellValue());
                group.setFoId((String) row.getCell(19).getStringCellValue());
                group.setBmId((String) row.getCell(21).getStringCellValue());
                group.setCentreName((String) row.getCell(23).getStringCellValue());
                groups.add(group);

                // Prepare prospect data
                Prospect prospect = new Prospect();
                String groupHead = row.getCell(12).getStringCellValue();
                prospect.setGrpHead((boolean) Boolean.parseBoolean(groupHead));

                prospect.setProspectId((String) row.getCell(0).getStringCellValue());
                prospect.setProspectName((String) row.getCell(1).getStringCellValue());
                prospect.setProspectMobile((Long) Math.round(row.getCell(5).getNumericCellValue()));
                prospect.setLoanAccNumber((Long) Math.round(row.getCell(7).getNumericCellValue()));
                prospect.setCifId((Long) Math.round(row.getCell(6).getNumericCellValue()));
                prospect.setLoanId((String) row.getCell(8).getStringCellValue());
                prospect.setLoanAmount((double) Math.round(row.getCell(13).getNumericCellValue()));
                prospects.add(prospect);
            }

            collectionRepository.saveAll(collections);
            groupRepository.saveAll(groups);
            prospectRepository.saveAll(prospects);
            workbook.close();
            return ResponseEntity.status(HttpStatus.OK).body("Data uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error:" + e.getMessage());
        }

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
