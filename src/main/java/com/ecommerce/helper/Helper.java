package com.ecommerce.helper;

import com.ecommerce.dtos.UserDto;
import com.ecommerce.entities.User;
import com.ecommerce.payloads.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {


    public static <U, V> PageableResponse<V> getPageableResponse(Page<U> all, Class<V> type) {
        List<U> users = all.getContent();

        List<V> dtoList = users.stream().map(user -> new ModelMapper().map(user, type)).collect(Collectors.toList());

        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageNumber(all.getNumber());
        response.setPageSize(all.getSize());
        response.setTotalElements(all.getTotalElements());
        response.setTotalPages(all.getTotalPages());
        response.setLastPage(all.isLast());

        return response;
    }
}
