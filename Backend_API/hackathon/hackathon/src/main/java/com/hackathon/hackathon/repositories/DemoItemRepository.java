package com.hackathon.hackathon.repositories;

import com.hackathon.hackathon.model.DemoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoItemRepository extends JpaRepository<DemoItem , Long> {


}
