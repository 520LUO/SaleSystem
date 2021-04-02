package com.MySystem.controller;


import com.MySystem.pojo.OutGoods;
import com.MySystem.pojo.SaleRecord;
import com.MySystem.pojo.StoreGoods;
import com.MySystem.pojo.WareHouse;
import com.MySystem.service.SaleService;
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
@RequestMapping("sale")
public class SaleController {


    @Autowired
    SaleService saleService;


    //访问销售记录列表界面
    @RequestMapping("/list")
    public String list(SaleRecord record, ModelMap m) {

        HashMap<String, Object> map = saleService.selectPage(record);
        //把数据传到前端
        m.addAttribute("info", map);
        m.addAttribute("convalue", record.getConvalue());
        m.addAttribute("condition", record.getCondition());
        return "salerecord/record-list";

    }

    //打开销售记录添加页面
    @RequestMapping("/insertPage")
    public String insert() {
        return "salerecord/record-insert";
    }

    //处理销售记录添加的ajax请求
    @RequestMapping("/saleInsertAjax")
    @ResponseBody
    public HashMap<String, Object> insertAjax(SaleRecord record) {
        HashMap<String, Object> map = new HashMap<>();
        String info = saleService.insert(record);
        map.put("info", info);
        return map;
    }

    //打开销售记录修改页面
    @RequestMapping("/updatePage")
    public String edit(SaleRecord record, ModelMap m) {
        //根据userId查询
        SaleRecord w = saleService.selectById(record);
        //将查询结果返回给前端
        m.addAttribute("record", w);
        return "salerecord/record-update";
    }


    //修改销售记录ajax请求
    @RequestMapping("/updateAjax")
    @ResponseBody
    public HashMap<String, Object> updateAjax(SaleRecord record) {
        HashMap<String, Object> map = new HashMap<>();
        String info = saleService.update(record);
        map.put("info", info);
        return map;
    }



    //销售记录导出excel
    @RequestMapping(value = "/saleExcel", method = RequestMethod.GET)
    public void igExcel(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("销售信息表");
        List<SaleRecord> list = saleService.userExcel();
        String fileName = "SaleRecordInfo" + ".xls";//设置导出文件的文件名

        //新增数据行，设置单元格数据
        int rowNum = 1;
        //headers表示excel的第一行的表头
        String[] headers = {"编号", "商品编号", "商品名称", "单价", "数量", "总价", "销售时间", "买家电话", "买家邮箱", "仓库地址", "销往地址"};
        HSSFRow row = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //将查询的数据放入对应的列
        for (SaleRecord record : list) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(record.getSaleId());
            row1.createCell(1).setCellValue(record.getSaleNum());
            row1.createCell(2).setCellValue(record.getSaleName());
            row1.createCell(3).setCellValue(record.getSalePrice());
            row1.createCell(4).setCellValue(record.getSaleCount());
            row1.createCell(5).setCellValue(record.getSaleTotal());
            row1.createCell(6).setCellValue(record.getSaleTime());
            row1.createCell(7).setCellValue(record.getBuyTel());
            row1.createCell(8).setCellValue(record.getBuyEmail());
            row1.createCell(9).setCellValue(record.getStoreArea());
            row1.createCell(10).setCellValue(record.getSaleArea());

            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());

    }

    //查询所有仓库地址
    @RequestMapping("/getArea")
    @ResponseBody
    public List<WareHouse> getArea() {
        return saleService.getArea();

    }

    //访问柱折线图页面
    @RequestMapping("/lineChart")
    public String lineChart() {
        return "salerecord/record-graph";
    }

    //处理折线图的ajax请求
    @RequestMapping("/lineChartAjax")
    @ResponseBody
    public HashMap<String, Object> lineChartAjax(SaleRecord record) {

        return saleService.lineChart(record);
    }

    //访问中国地图页面
    @RequestMapping("/china")
    public String china() {
        return "salerecord/record-china";
    }

    //获取所有包含经纬度地址的销售记录 chinaAjax
    @RequestMapping("/chinaAjax")
    @ResponseBody
    List<HashMap<String, Object>> chinaAjax(SaleRecord record) {

        return saleService.getRecord(record);
    }

    //根据商品编号获取库存信息
    @RequestMapping("/getSgAjax")
    @ResponseBody
    public HashMap<String, Object> getSgOutAjax(String saleNum) {
        HashMap<String, Object> map = new HashMap<>();
        StoreGoods store = saleService.selectFromStore(saleNum);
        map.put("info", store);
        return map;
    }
}
