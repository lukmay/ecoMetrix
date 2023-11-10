package com.hackathon.hackathon.services;

import com.hackathon.hackathon.dto.DemoItemDTO;
import com.hackathon.hackathon.model.DemoItem;
import com.hackathon.hackathon.repositories.DemoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoItemService {
    @Autowired
    private DemoItemRepository demoItemRepository;

    public void setNewItem(boolean item_value) {
        DemoItem item = new DemoItem();
        item.setBooleanSet(item_value);
        demoItemRepository.save(item);
    }

    public List<DemoItem> getAllItems(){
        return demoItemRepository.findAll();
    }

}
