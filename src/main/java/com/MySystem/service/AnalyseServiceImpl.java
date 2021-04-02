package com.MySystem.service;

import com.MySystem.dao.AnalyseDao;
import com.MySystem.pojo.Analysis;
import com.MySystem.pojo.IncomeGoods;
import com.MySystem.pojo.OutGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AnalyseServiceImpl implements AnalyseService {

    @Autowired
    AnalyseDao analyseDao;

    //生成与统计曲线图
    @Override
    public HashMap<String, Object> lineIgGraph(Analysis analys) {
        HashMap<String, Object> map = new HashMap<>();
        analys.setTime(analys.getConvalue() + "%");   //用通配符进行数据库查询
        String time;
        String name;
        List<IncomeGoods> list = analyseDao.incomeGraph(analys);
        List<Analysis> listA = new ArrayList<>();
        List<Analysis> listI = new ArrayList<>();
        if ("年统计".equals(analys.getCondition())) {
            for (IncomeGoods income : list) {
                income.setIgTime(income.getIgTime().substring(0, 7));
            }
        }
        //统计出每天的销售量和销售金额
        for (int i = 0; i < list.size(); ) {
            String incomeTime = list.get(i).getIgTime();
            if ("月统计".equals(analys.getCondition())) {
                time = incomeTime.substring(8) + "日";//从完整时间字符串里面截取出dayTime
            } else {
                time = incomeTime.substring(5, 7) + "月";//从完整时间字符串里面截取出monthTime
            }
            Analysis analysis = new Analysis(0, 0, time);

            for (int j = i; j < list.size(); j++) {
                if (list.get(j).getIgTime().equals(incomeTime)) {
                    analysis.setCount(analysis.getCount() + list.get(j).getIgCount());
                    analysis.setMoney(analysis.getMoney() + list.get(j).getIgTotal());
                    i = j + 1;
                } else {
                    i = j;
                    break;
                }
            }
            listA.add(analysis);
        }
        //根据商品名筛选出柱状图需要的数据
        for (int i = 0; i < list.size(); i++) {
            name = list.get(i).getIgName();
            Analysis analysis = new Analysis(name, 0, 0);
            for (int j = 0; j < list.size(); j++) {
                if (name.equals(list.get(j).getIgName())) {
                    analysis.setCount(analysis.getCount() + list.get(j).getIgCount());
                    analysis.setMoney(analysis.getMoney() + list.get(j).getIgTotal());
                    list.remove(j);
                    j--;    //每次移除元素j--,防止指标跳跃元素
                }
            }
            i--;    //每次移除了元素要i--
            listI.add(analysis);
        }
        for (Analysis a : listI) {
            System.out.println(a);
        }
        map.put("info", listA);
        map.put("listI", listI);

        return map;
    }

    //出库统计图
    public HashMap<String, Object> lineOgGraph(Analysis analys) {
        HashMap<String, Object> map = new HashMap<>();
        analys.setTime(analys.getConvalue() + "%");   //用通配符进行数据库查询
        String time;
        String name;
        List<OutGoods> list = analyseDao.outGraph(analys);
        List<Analysis> listA = new ArrayList<>();
        List<Analysis> listI = new ArrayList<>();
        if ("年统计".equals(analys.getCondition())) {
            for (OutGoods out : list) {
                out.setOgTime(out.getOgTime().substring(0, 7));
            }
        }
        //统计出每天的销售量和销售金额
        for (int i = 0; i < list.size(); ) {
            String outTime = list.get(i).getOgTime();
            if ("月统计".equals(analys.getCondition())) {
                time = outTime.substring(8) + "日";//从完整时间字符串里面截取出dayTime
            } else {
                time = outTime.substring(5, 7) + "月";//从完整时间字符串里面截取出monthTime
            }
            Analysis analysis = new Analysis(0, 0, time);

            for (int j = i; j < list.size(); j++) {
                if (list.get(j).getOgTime().equals(outTime)) {
                    analysis.setCount(analysis.getCount() + list.get(j).getOgCount());
                    analysis.setMoney(analysis.getMoney() + list.get(j).getOgTotal());
                    i = j + 1;
                } else {
                    i = j;
                    break;
                }
            }
            listA.add(analysis);
        }
        //根据商品名筛选出柱状图需要的数据
        for (int i = 0; i < list.size(); i++) {
            name = list.get(i).getOgName();
            Analysis analysis = new Analysis(name, 0, 0);
            for (int j = 0; j < list.size(); j++) {
                if (name.equals(list.get(j).getOgName())) {
                    analysis.setCount(analysis.getCount() + list.get(j).getOgCount());
                    analysis.setMoney(analysis.getMoney() + list.get(j).getOgTotal());
                    list.remove(j);
                    j--;    //每次移除元素j--,防止指标跳跃元素
                }
            }
            i--;    //每次移除了元素要i--
            listI.add(analysis);
        }
        for (Analysis a : listI) {
            System.out.println(a);
        }
        map.put("info", listA);
        map.put("listI", listI);

        return map;

    }
}
