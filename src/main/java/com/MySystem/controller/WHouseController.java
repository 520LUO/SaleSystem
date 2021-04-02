package com.MySystem.controller;


import com.MySystem.pojo.SupplyInfo;
import com.MySystem.pojo.WareHouse;
import com.MySystem.service.SupplyService;
import com.MySystem.service.WHouseService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("ware")
public class WHouseController {

    //获取图片上传的配置路径
    @Value("${file.address}")
    String fileAdress;

    //用户访问的图片路径
    @Value("${file.staticPath}")
    String upload;

    @Autowired
    WHouseService wHouseService;

    //访问管理用户列表界面
    @RequestMapping("/list")
    public String list(WareHouse ware, ModelMap m) {

        HashMap<String, Object> map = wHouseService.selectPage(ware);
        //把数据传到前端
        m.addAttribute("info", map);
        m.addAttribute("convalue", ware.getConvalue());
        m.addAttribute("condition", ware.getCondition());
        return "whouse/whouse-list";

    }

    //访问添加页面
    //打开添加页面
    @RequestMapping("/insertPage")
    public String insert() {

        return "whouse/whouse-insert";
    }

    //处理添加的ajax请求
    @RequestMapping("/insertAjax")
    @ResponseBody
    public HashMap<String, Object> insertAjax(WareHouse ware) {
        HashMap<String, Object> map = new HashMap<>();
        String info = wHouseService.insert(ware);
        map.put("info", info);
        return map;
    }

    //打开修改页面
    @RequestMapping("/updatePage")
    public String edit(WareHouse ware, ModelMap m) {
        //根据userId查询
        WareHouse w = wHouseService.selectById(ware);
        //将查询结果返回给前端
        m.addAttribute("ware", w);
        return "whouse/whouse-update";
    }


    //修改ajax请求
    @RequestMapping("/updateAjax")
    @ResponseBody
    public HashMap<String, Object> updateAjax(WareHouse ware) {
        HashMap<String, Object> map = new HashMap<>();
        String info = wHouseService.update(ware);
        map.put("info", info);
        return map;
    }

    //删除ajax请求
    @RequestMapping("/deleteAjax")
    @ResponseBody
    public HashMap<String, Object> deleteAjax(WareHouse ware) {
        HashMap<String, Object> map = new HashMap<>();
        String info = wHouseService.delete(ware);
        map.put("info", info);
        return map;
    }




    //导出excel
    @RequestMapping(value = "/wareExcel", method = RequestMethod.GET)
    public void userExcel(HttpServletResponse response) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("仓库信息表");
        List<WareHouse> list = wHouseService.userExcel();
        String fileName = "WareHouseInfo" + ".xls";//设置导出文件的文件名

        //新增数据行，设置单元格数据
        int rowNum = 1;
        //headers表示excel的第一行的表头
        String[] headers = {"编号","仓库编号", "仓库地址", "仓库面积", "货架号", "仓库类别", "备注"};
        HSSFRow row = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //将查询的数据放入对应的列
        for (WareHouse ware : list) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(ware.getWhId());
            row1.createCell(1).setCellValue(ware.getWhNum());
            row1.createCell(2).setCellValue(ware.getWhAdress());
            row1.createCell(3).setCellValue(ware.getWhArea());
            row1.createCell(4).setCellValue(ware.getShelves());
            row1.createCell(5).setCellValue(ware.getWhClass());
            row1.createCell(6).setCellValue(ware.getWhNote());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());

    }

}
