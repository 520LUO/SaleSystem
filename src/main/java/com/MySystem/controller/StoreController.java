package com.MySystem.controller;


import com.MySystem.pojo.StoreGoods;
import com.MySystem.service.StoreService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("store")
public class StoreController {


    @Autowired
    StoreService storeService;


    //访问入库商品列表界面
    @RequestMapping("/list")
    public String list(StoreGoods store, ModelMap m) {

        HashMap<String, Object> map = storeService.selectPage(store);
        //把数据传到前端
        m.addAttribute("info", map);
        m.addAttribute("convalue", store.getConvalue());
        m.addAttribute("condition", store.getCondition());
        return "storegoods/store-list";

    }

    //打开入库商品添加页面
    @RequestMapping("/insertPage")
    public String insert() {

        return "StoreGoods/store-insert";
    }

    //处理入库商品添加的ajax请求
    @RequestMapping("/insertAjax")
    @ResponseBody
    public HashMap<String, Object> insertAjax(StoreGoods store) {
        HashMap<String, Object> map = new HashMap<>();
        String info = storeService.insert(store);
        map.put("info", info);
        return map;
    }

    //打开入库商品修改页面
    @RequestMapping("/updatePage")
    public String edit(StoreGoods store, ModelMap m) {
        //根据userId查询
        StoreGoods w = storeService.selectById(store);
        //将查询结果返回给前端
        m.addAttribute("store", w);
        return "storegoods/store-update";
    }


    //修改入库商品ajax请求
    @RequestMapping("/updateAjax")
    @ResponseBody
    public HashMap<String, Object> updateAjax(StoreGoods store) {
        HashMap<String, Object> map = new HashMap<>();
        String info = storeService.update(store);
        map.put("info", info);
        return map;
    }

    //删除入库商品ajax请求
    @RequestMapping("/deleteAjax")
    @ResponseBody
    public HashMap<String, Object> deleteAjax(StoreGoods store) {
        HashMap<String, Object> map = new HashMap<>();
        String info = storeService.delete(store);
        map.put("info", info);
        return map;
    }



    //入库商品导出excel
    @RequestMapping(value = "/igExcel", method = RequestMethod.GET)
    public void igExcel(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("出入库信息表");
        List<StoreGoods> list = storeService.userExcel();
        String fileName = "StoreGoodsInfo" + ".xls";//设置导出文件的文件名

        //新增数据行，设置单元格数据
        int rowNum = 1;
        //headers表示excel的第一行的表头
        String[] headers = {"编号", "商品编号", "商品类别", "单价", "数量", "总价", "入库时间", "入库人", "供货商"};
        HSSFRow row = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //将查询的数据放入对应的列
        for (StoreGoods store : list) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(store.getSgId());
            row1.createCell(1).setCellValue(store.getSgNum());
            row1.createCell(2).setCellValue(store.getSgClass());
            row1.createCell(3).setCellValue(store.getSgPrice());
            row1.createCell(4).setCellValue(store.getSgCount());
            row1.createCell(5).setCellValue(store.getSgTotal());
            row1.createCell(6).setCellValue(store.getSgTime());
            row1.createCell(7).setCellValue(store.getSgSupply());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());

    }

    //访问柱状图页面
    @RequestMapping("/histogram")
    public String histogram() {
        return "storegoods/histogram";
    }

    //柱状图
    //处理柱状图的ajax请求
    @RequestMapping("/histogramAjax")
    @ResponseBody
    public HashMap<String, Object> histogramAjax(StoreGoods store) {
        return storeService.histogram(store);
    }

}
