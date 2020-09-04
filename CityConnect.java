//This is a sample program based on springboot to check that there exists a path between source city and destination city
package com.cityconnect.controller;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path="/connected",  method = RequestMethod.GET, produces=MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String processForm(@RequestParam(defaultValue="None") String source,
                              @RequestParam(defaultValue="None") String destination) {

        if(source=="None"||destination=="None"||!source.matches("[a-zA-Z]+")||!destination.matches("[a-zA-Z]+")) res = "No";
        

       // this graph is directional   
        City_Graph graph = new City_Graph();
        String path = "C:\\Route.txt";

         try {
           // Please replace the path string to point to location of file in your machine 
        BufferedReader in = new BufferedReader(
                               new FileReader(path));
        String str;

        while ((str = in.readLine())!= null) {
            String[] ar=str.split(",");
             graph.addDoublePath(ar[0], ar[1]);
        }
        in.close();
       } catch (IOException e) {
       // System.out.println("File Read Error");
        res = "No";
       }
       
        LinkedList<String> visited = new LinkedList();
        SOURCE = source; 
        DESTINATION = destination;
 
        visited.add(SOURCE);
        res = new City_Graph().breadthFirst(graph, visited);
        sc.close(); 
        return res; // Returns response
    }
}

public class City_Graph
{
    private Map<String, LinkedHashSet<String>> map = new HashMap();
 
    public void addPath(String city1, String city2)
    {
        LinkedHashSet<String> adjacent = map.get(city1);
        if (adjacent == null)
        {
            adjacent = new LinkedHashSet();
            map.put(city1, adjacent);
        }
        adjacent.add(city2);
    }
 
    public void addDoublePath(String city1, String city2)
    {
        addPath(city1, city2);
        addPath(city2, city1);
    }
 
    public boolean isConnected(String city1, String city2)
    {
        Set adjacent = map.get(city1);
        if (adjacent == null)
        {
            return false;
        }
        return adjacent.contains(city2);
    }
 
    public LinkedList<String> adjacentCity(String last)
    {
        LinkedHashSet<String> adjacent = map.get(last);
        if (adjacent == null)
        {
            return new LinkedList();
        }
        return new LinkedList<String>(adjacent);
    }
 
    private static String  SOURCE;
    private static String  DESTINATION;
    private static boolean flag;
  
 
    private String breadthFirst(City_Graph graph,
            LinkedList<String> visited)
    { 
        LinkedList<String> cities = graph.adjacentCity(visited.getLast()); String res;
 
        for (String city : cities)
        {
            if (visited.contains(city))
            {
                continue;
            }
            if (city.equals(DESTINATION))
            {
                visited.add(city);
                res = displayRoute(visited);
                flag = true;
                visited.removeLast();
                break;
            }
        }
 
        for (String city : cities)
        {
            if (visited.contains(city) || city.equals(DESTINATION))
            {
                continue;
            }
            visited.addLast(city);
            breadthFirst(graph, visited);
            visited.removeLast();
        }
        if (flag == false)
        {
            flag = true;
            res = "No";
        }
 
        return res;

    }
 
    private String displayRoute(LinkedList<String> visited)
    {
        if (flag == false)
            return "Yes"; 
    }
}