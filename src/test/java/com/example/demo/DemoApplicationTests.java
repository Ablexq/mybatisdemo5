package com.example.demo;

import com.example.demo.dao.BookDao;
import com.example.demo.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    BookDao bookDao;

    @Test
    public void test1() {
        List<Book> all = bookDao.getAll(50D);
        for (int i = 0; i < all.size(); i++) {
            System.out.println(i + "======" + all.get(i).toString());
        }
    }

    @Test
    public void test2() {
        List<Book> all = bookDao.getAll2();
        for (int i = 0; i < all.size(); i++) {
            System.out.println(i + "======" + all.get(i).toString());
        }
    }
}

