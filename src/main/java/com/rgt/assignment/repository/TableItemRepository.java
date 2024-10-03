package com.rgt.assignment.repository;

import com.rgt.assignment.entity.TableItem;
import com.rgt.assignment.entity.Tables;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableItemRepository extends JpaRepository<TableItem, Long> {
    List<TableItem> findByTables(Tables tables);
}
