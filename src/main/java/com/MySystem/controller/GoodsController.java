package com.MySystem.controller;


import com.MySystem.pojo.IncomeGoods;
import com.MySystem.pojo.OutGoods;
import com.MySystem.pojo.StoreGoods;
import com.MySystem.service.GoodsService;
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
@RequestMapping("goods")
public class GoodsController {


    @Autowired
    GoodsService goodsService;


    //访问入库商品列表界面
    @RequestMapping("/list")
    public String list(IncomeGoods income, ModelMap m) {

        HashMap<String, Object> map = goodsService.selectPage(income);
        //把数据传到前端
        m.addAttribute("info", map);
        m.addAttribute("convalue", income.getConvalue());
        m.addAttribute("condition", income.getCondition());
        return "incomegoods/income-list";

    }

    //打开入库商品添加页面
    @RequestMapping("/insertPage")
    public String insert() {

        return "incomegoods/income-insert";
    }

    //处理入库商品添加的ajax请求
    @RequestMapping("/insertAjax")
    @ResponseBody
    public HashMap<String, Object> insertAjax(IncomeGoods income) {
        HashMap<String, Object> map = new HashMap<>();
        String info = goodsService.insert(income);
        map.put("info", info);
        return map;
    }

    //打开入库商品修改页面
    @RequestMapping("/updatePage")
    public String edit(IncomeGoods income, ModelMap m) {
        //根据userId查询
        IncomeGoods w = goodsService.selectById(income);
        //将查询结果返回给前端
        m.addAttribute("income", w);
        return "incomegoods/income-update";
    }


    //修改入库商品ajax请求
    @RequestMapping("/updateAjax")
    @ResponseBody
    public HashMap<String, Object> updateAjax(IncomeGoods income) {
        HashMap<String, Object> map = new HashMap<>();
        String info = goodsService.update(income);
        map.put("info", info);
        return map;
    }

    //根据商品编号获取库存信息
    @RequestMapping("/getSgAjax")
    @ResponseBody
    public HashMap<String, Object> getSgAjax(IncomeGoods income) {
        HashMap<String, Object> map = new HashMap<>();
        StoreGoods store = goodsService.selectByIgNum(income);
        map.put("info", store);
        return map;
    }


    //删除入库商品ajax请求
    @RequestMapping("/deleteAjax")
    @ResponseBody
    public HashMap<String, Object> deleteAjax(IncomeGoods income) {
        HashMap<String, Object> map = new HashMap<>();
        String info = goodsService.delete(income);
        map.put("info", info);
        return map;
    }

    //访问出库商品列表界面
    @RequestMapping("/outlist")
    public String outlist(OutGoods out, ModelMap m) {

        HashMap<String, Object> map = goodsService.selectOutPage(out);
        //把数据传到前端
        m.addAttribute("info", map);
        m.addAttribute("convalue", out.getConvalue());
        m.addAttribute("condition", out.getCondition());
        return "outgoods/out-list";

    }

    //打开出库商品添加页面
    @RequestMapping("/outInsertPage")
    public String outInsert() {

        return "outgoods/out-insert";
    }

    //处理添加出库商品的ajax请求
    @RequestMapping("/outInsertAjax")
    @ResponseBody
    public HashMap<String, Object> outInsertAjax(OutGoods out) {
        HashMap<String, Object> map = new HashMap<>();
        String info = goodsService.outInsert(out);
        map.put("info", info);
        return map;
    }

    //打开出库商品修改页面
    @RequestMapping("/outUpdatePage")
    public String outEdit(OutGoods out, ModelMap m) {
        //根据userId查询
        OutGoods w = goodsService.selectByOutId(out);
        //将查询结果返回给前端
        m.addAttribute("out", w);
        return "outgoods/out-update";
    }


    //修改出库商品ajax请求
    @RequestMapping("/outUpdateAjax")
    @ResponseBody
    public HashMap<String, Object> outUpdateAjax(OutGoods out) {
        HashMap<String, Object> map = new HashMap<>();
        String info = goodsService.outUpdate(out);
        map.put("info", info);
        return map;
    }

    //根据商品编号获取库存信息
    @RequestMapping("/getSgOutAjax")
    @ResponseBody
    public HashMap<String, Object> getSgOutAjax(OutGoods out) {
        HashMap<String, Object> map = new HashMap<>();
        StoreGoods store = goodsService.selectByOgNum(out);
        map.put("info", store);
        return map;
    }

    //删除出库商品ajax请求
    @RequestMapping("/outDeleteAjax")
    @ResponseBody
    public HashMap<String, Object> outDeleteAjax(OutGoods out) {
        HashMap<String, Object> map = new HashMap<>();
        String info = goodsService.outDelete(out);
        map.put("info", info);
        return map;
    }


    //入库商品导出excel
    @RequestMapping(value = "/igExcel", method = RequestMethod.GET)
    public void igExcel(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("出入库信息表");
        List<IncomeGoods> list = goodsService.userExcel();
        String fileName = "IncomeGoodsInfo" + ".xls";//设置导出文件的文件名

        //新增数据行，设置单元格数据
        int rowNum = 1;
        //headers表示excel的第一行的表头
        String[] headers = {"编号", "商品编号", "商品名称", "商品类别", "单价", "数量", "总价", "入库时间", "入库人", "供货商"};
        HSSFRow row = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //将查询的数据放入对应的列
        for (IncomeGoods income : list) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(income.getIgId());
            row1.createCell(1).setCellValue(income.getIgNum());
            row1.createCell(2).setCellValue(income.getIgName());
            row1.createCell(3).setCellValue(income.getIgClass());
            row1.createCell(4).setCellValue(income.getIgPrice());
            row1.createCell(5).setCellValue(income.getIgCount());
            row1.createCell(6).setCellValue(income.getIgTotal());
            row1.createCell(7).setCellValue(income.getIgTime());
            row1.createCell(8).setCellValue(income.getIgPerson());
            row1.createCell(9).setCellValue(income.getIgSupply());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());

    }

    //出库商品导出excel
    @RequestMapping(value = "/ogExcel", method = RequestMethod.GET)
    public void ogsExcel(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("出入库信息表");
        List<OutGoods> list = goodsService.outGoodsExcel();
        String fileName = "OutGoodsInfo" + ".xls";//设置导出文件的文件名

        //新增数据行，设置单元格数据
        int rowNum = 1;
        //headers表示excel的第一行的表头
        String[] headers = {"编号", "商品编号","商品名称",  "商品类别", "单价", "数量", "总价", "出库时间", "出库人", "供货商"};
        HSSFRow row = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //将查询的数据放入对应的列
        for (OutGoods out : list) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(out.getOgId());
            row1.createCell(1).setCellValue(out.getOgNum());
            row1.createCell(2).setCellValue(out.getOgName());
            row1.createCell(3).setCellValue(out.getOgClass());
            row1.createCell(4).setCellValue(out.getOgPrice());
            row1.createCell(5).setCellValue(out.getOgCount());
            row1.createCell(6).setCellValue(out.getOgTotal());
            row1.createCell(7).setCellValue(out.getOgTime());
            row1.createCell(8).setCellValue(out.getOgPerson());
            row1.createCell(9).setCellValue(out.getOgSupply());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());

    }

}
