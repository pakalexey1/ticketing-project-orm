package com.cydeo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.lang.reflect.Type;

@Component
public class MapperUtil {

    private final ModelMapper modelMapper;

    public MapperUtil(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public String getUsername(){

        return null;
    }

        public <T> T convert(Object objectToBeConverted, T convertedObject){

        return modelMapper.map(objectToBeConverted, (Type) convertedObject.getClass());
    }

//    public <T> T convertToEntity(Object objectToBeConverted, T convertedObject){
//
//        return modelMapper.map(objectToBeConverted, (Type) convertedObject.getClass());
//    }
//
//    public <T> T convertToDTO(Object objectToBeConverted, T convertedObject){
//
//        return modelMapper.map(objectToBeConverted, (Type) convertedObject.getClass());
//
//    }
}
