package com.example.contentservice.specification;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableSorting {
  public static  Pageable getPageable(List<String> sortBy,String sortOrder,int pageNumber,int pageSize){
    if(sortBy==null || sortBy.isEmpty()){
      return  PageRequest.of(pageNumber-1,pageSize);
    }
    String[] fieldsSort= sortBy.toArray(String[]::new);
    if("desc".equals(sortOrder)){
      return PageRequest.of(pageNumber-1,pageSize, Sort.by(fieldsSort).descending());
    }
    return PageRequest.of(pageNumber-1,pageSize, Sort.by(fieldsSort).ascending());
  }
}
