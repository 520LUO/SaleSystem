package com.MySystem.controller;


import com.MySystem.pojo.SupplyInfo;
import com.MySystem.pojo.UserInfo;
import com.MySystem.service.SupplyService;
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
@RequestMapping("supply")
public class SupplyController {

    //获取图片上传的配置路径
    @Value("${file.address}")
    String fileAdress;

    //用户访问的图片路径
    @Value("${file.staticPath}")
    String upload;

    @Autowired
    SupplyService supplyService;

    //访问管理用户列表界面
    @RequestMapping("/list")
    public String list(SupplyInfo supply, ModelMap m) {

        HashMap<String, Object> map = supplyService.selectPage(supply);
        //把数据传到前端
        m.addAttribute("info", map);
        m.addAttribute("convalue", supply.getConvalue());
        m.addAttribute("condition", supply.getCondition());
        return "supplier/supply-list";

    }

    //访问添加页面
    @RequestMapping("/insertPage")
    public String insert() {

        return "supplier/supply-insert";
    }

    //处理添加的ajax请求
    @RequestMapping("/insertAjax")
    @ResponseBody
    public HashMap<String, Object> insertAjax(SupplyInfo supply) {
        HashMap<String, Object> map = new HashMap<>();
        String info = supplyService.insert(supply);
        map.put("info", info);
        return map;
    }

    //打开修改页面
    @RequestMapping("/updatePage")
    public String edit(SupplyInfo supply, ModelMap m) {
        //根据userId查询
        SupplyInfo u = supplyService.selectById(supply);
        //将查询结果返回给前端
        m.addAttribute("supply", u);
        return "supplier/supply-update";
    }

    //打开头像页面
    @RequestMapping("/lookPhoto")
    public String lookPhoto(SupplyInfo supply, ModelMap m) {
        //根据userId查询
        SupplyInfo u = supplyService.selectById(supply);
        //将查询结果返回给前端
        m.addAttribute("supply", u);
        return "supplier/lookphoto";
    }

    //修改ajax请求
    @RequestMapping("/updateAjax")
    @ResponseBody
    public HashMap<String, Object> updateAjax(SupplyInfo supply) {
        HashMap<String, Object> map = new HashMap<>();
        String info = supplyService.update(supply);
        map.put("info", info);
        return map;
    }

    //删除ajax请求
    @RequestMapping("/deleteAjax")
    @ResponseBody
    public HashMap<String, Object> deleteAjax(SupplyInfo supply) {
        HashMap<String, Object> map = new HashMap<>();
        String info = supplyService.delete(supply);
        map.put("info", info);
        return map;
    }


    //图片上传
    //用户名输入框失去焦点事件,判断用户名是否重名
    @RequestMapping("/uploadAjax")
    @ResponseBody
    public HashMap<String, Object> uploadAjax(MultipartFile file) {
        HashMap<String, Object> map = new HashMap<>();
        //上传文件
        try {
            //定义一个文件上传目录
            String timeDir;
            //获取时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            timeDir = sdf.format(new Date());

            //定义文件上传的前缀
            String pre;
            //为了保证文件上传到服务器文件名是唯一的
            pre = UUID.randomUUID() + "";//生成比较大的随机数转为字符串
            //获取文件的后缀名 jpg
            String hou = "";
            if (file != null) {
                String originalName = file.getOriginalFilename();
                hou = originalName.substring(originalName.lastIndexOf(".") + 1);//截取'.'后的后缀名

            }
            //定义文件上传的全路径
            String filePath = fileAdress + "/" + timeDir + "/" + pre + "." + hou;
            //创建file对象
            File f = new File(filePath);
            //判断目录是否存在，不存在就创建目录
            if (!f.isDirectory()) {
                f.mkdirs();
            }
            //上传文件
            file.transferTo(f);
            map.put("code", 0);//设置上传成功的提示信息
            //返回前端 用户访问图片的路径
            String path = upload + "/" + timeDir + "/" + pre + "." + hou;//完整的图片地址
            map.put("src", path);
            return map;

        } catch (IOException e) {
            e.printStackTrace();
            map.put("code", 1);//设置上传失败的提示信息
            return map;

        }
    }

    //导出excel
    @RequestMapping(value = "/supplyExcel", method = RequestMethod.GET)
    public void userExcel(HttpServletResponse response) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("供货商信息表");
        List<SupplyInfo> list = supplyService.userExcel();
        String fileName = "Suppliers" + ".xls";//设置导出文件的文件名

        //新增数据行，设置单元格数据
        int rowNum = 1;
        //headers表示excel的第一行的表头
        String[] headers = {"编号", "供货商代理人", "性别", "联系电话", "邮箱地址", "证件名称", "证件号码","签发时间","有效期限"};
        HSSFRow row = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //将查询的数据放入对应的列
        for (SupplyInfo supply : list) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(supply.getSupId());
            row1.createCell(1).setCellValue(supply.getSupAgent());
            row1.createCell(2).setCellValue(supply.getSex());
            row1.createCell(3).setCellValue(supply.getSupTel());
            row1.createCell(4).setCellValue(supply.getSupEmail());
            row1.createCell(5).setCellValue(supply.getCertName());
            row1.createCell(6).setCellValue(supply.getCertNum());
            row1.createCell(7).setCellValue(supply.getStartTime());
            row1.createCell(8).setCellValue(supply.getValidTime());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());

    }

}
