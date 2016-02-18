package com.fenixinfotech.spring.playpen.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BasicController
{
    @RequestMapping("/ping")
    @ResponseBody
    public String ping(@RequestParam(value="param", required = false, defaultValue = "EMPTY") String param)
    {
        return "PING " + param;
    }
}