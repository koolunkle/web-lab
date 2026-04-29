package com.eazybytes.eazystore.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.eazystore.dto.UserDto;

@RestController
@RequestMapping("api/v1/dummy")
public class DummyController {

    @PostMapping("/create-user")
    public String createUser(@RequestBody UserDto userDto) {
        System.out.println(userDto);
        return "User created successfully";
    }

    // @GetMapping("/headers")
    // public String readHeaders(@RequestHeader(name = "User-Agent") String
    // userAgent,
    // @RequestHeader(name = "User-Location", required = false) String userLocation)
    // {
    // return "Received headers with value : " + userAgent + " : " + userLocation;
    // }

    // @GetMapping("/headers")
    // public String readHeaders(@RequestHeader Map<String, String> headers) {
    // return "Received headers with value : " + headers.toString();
    // }

    @GetMapping("/headers")
    public String readHeaders(@RequestHeader HttpHeaders headers) {
        List<String> location = headers.get("User-Location");
        return "Received headers with value : " + location;
    }

    @GetMapping("/search")
    public String searchUser(@RequestParam(required = false, defaultValue = "Guest", name = "name") String userName) {
        return "Searching for user : " + userName;
    }

    @GetMapping("/multiple-search")
    public String multipleSearch(@RequestParam Map<String, String> params) {
        return "Searching for user : " + params.get("firstName") + " " + params.get("lastName");
    }

    // @GetMapping({ "/user/{userId}/posts/{postId}", "/user/{userId}" })
    // public String getUser(@PathVariable(name = "userId") String id,
    // @PathVariable(required = false) String postId) {
    // return "Searching for user : " + id + " and post : " + postId;
    // }

    @GetMapping({ "/user/map/{userId}/posts/{postId}", "/user/map/{userId}" })
    public String getUserUsingMap(@PathVariable Map<String, String> pathVariables) {
        return "Searching for user : " + pathVariables.get("userId") + " and post : " + pathVariables.get("postId");
    }
}
