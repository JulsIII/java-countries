package com.lambdaschool.countries.controllers;


import com.lambdaschool.countries.models.Country;
import com.lambdaschool.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController
{
    private List<Country> findCountries(List<Country> myList, CheckCountry tester)
    {
        List<Country> tempList = new ArrayList<>();

        for (Country c : myList)
        {
            if (tester.test(c))
            {
                tempList.add(c);
            }
        }

        return tempList;
    }

    @Autowired
    CountryRepository countrepos;

    //http://localhost:2021/names/all
    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountry()
    {
        List<Country> myList = new ArrayList<>();
        countrepos.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    //http://localhost:2021/names/start/{letter}
    @GetMapping(value = "/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> listCountryName(@PathVariable char letter)
    {
        List<Country> myList = new ArrayList<>();
        countrepos.findAll().iterator().forEachRemaining(myList::add);

        List<Country> rtnList = findCountries(myList, c-> c.getName().charAt(0) == letter);

        for (Country c : rtnList)
        {
            System.out.println(c);
        }

//        // total of all salaries
//        double total = 0;
//        for (Country c : myList)
//        {
//            total = total + c.getSalary();
//        }
//        System.out.println(total);

        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }
}
