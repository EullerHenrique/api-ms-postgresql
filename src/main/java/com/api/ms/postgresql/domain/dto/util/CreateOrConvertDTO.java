package com.api.ms.postgresql.domain.dto.util;

import org.modelmapper.ModelMapper;

public class CreateOrConvertDTO {

    public static Object create(Object object)  {
        ModelMapper modelMapper = new ModelMapper();

        Class<?> c = null;
        try {
            c = Class.forName(object.getClass().getName().replace("model", "dto") +"DTO");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return modelMapper.map(object, c);
    }


    public static Object convert(Object objectDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Class<?> c = null;
        try {
            c = Class.forName(objectDTO.getClass().getName().replace("dto", "model").replace("DTO", ""));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return modelMapper.map(objectDTO, c);
    }

}
