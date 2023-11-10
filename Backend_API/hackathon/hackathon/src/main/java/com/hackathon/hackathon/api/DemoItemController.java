package com.hackathon.hackathon.api;

import com.hackathon.hackathon.dto.DemoItemDTO;
import com.hackathon.hackathon.model.DemoItem;
import com.hackathon.hackathon.services.DemoItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
@Tag(name="Items")
public class DemoItemController {

    @Autowired
    private DemoItemService demoItemService;

    @PostMapping("/{boolean_set}")
    public ResponseEntity<Object> addDemoItem(@PathVariable("boolean_set") Boolean boolean_sez) {
        demoItemService.setNewItem(boolean_sez);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllDemoItems() {


        List<DemoItem> dataList = demoItemService.getAllItems();
        return ResponseEntity.ok(dataList);

    }




}
