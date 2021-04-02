package com.MySystem.service;

import com.MySystem.pojo.Analysis;

import java.util.HashMap;

public interface AnalyseService {

    //入库统计图
    HashMap <String,Object> lineIgGraph(Analysis analys);

    //出库统计图
    public HashMap<String, Object> lineOgGraph(Analysis analys);
}
