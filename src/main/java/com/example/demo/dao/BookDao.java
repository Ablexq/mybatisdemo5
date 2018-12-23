package com.example.demo.dao;

import com.example.demo.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookDao {
    @Select("select name,author,price from book where price = #{price}")
    @Results({
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "price", column = "price", javaType = Double.class)
    })
    List<Book> getAll(double price);

    @Select("select name,author,price from book where price = ${50}")
    @Results({
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "price", column = "price", javaType = Double.class)
    })
    List<Book> getAll2();
}
