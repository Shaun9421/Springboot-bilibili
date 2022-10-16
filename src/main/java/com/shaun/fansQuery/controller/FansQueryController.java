package com.shaun.fansQuery.controller;

import com.shaun.fansQuery.service.FansQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fansQuery")
public class FansQueryController {

    @Resource
    private FansQueryService fansQueryService;

    @GetMapping("/all")
    public void  fansQuery() throws Exception {
        fansQueryService.fansQuery();
    }


}
