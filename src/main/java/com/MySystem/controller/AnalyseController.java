package com.MySystem.controller;

import com.MySystem.pojo.Analysis;
import com.MySystem.service.AnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("analyse")
public class AnalyseController {
    @Autowired
    AnalyseService analyseService;

    //访问入库统计页面
    @RequestMapping("/analyChart")
    public String lineIgChart() {
        return "analysis/analysis-chart";
    }

    //处理折线图的ajax请求
    @RequestMapping("/lineChartAjax")
    @ResponseBody
    public HashMap<String, Object> iGChartAjax(Analysis analys) {
        HashMap<String, Object> map;
        if ("入库".equals(analys.getType())) {
            map = analyseService.lineIgGraph(analys);
        } else {
            map = analyseService.lineOgGraph(analys);
        }
        return map;
    }


}
