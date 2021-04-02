package com.MySystem.controller;

import com.MySystem.pojo.UserInfo;
import com.MySystem.service.AdminService;
import com.MySystem.service.SuperService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("super")
public class SuperController {
    @Autowired
    AdminService adminService;

    @Autowired
    SuperService superService;
    //获取图片上传的配置路径
    @Value("${file.address}")
    String fileAdress;

    //创建redis库的操作对象
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    //用户访问的图片路径
    @Value("${file.staticPath}")
    String upload;

    //访问管理用户列表界面
    @RequestMapping("/list")
    public String list(UserInfo user, ModelMap m, HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        if (true) {//判断权限等级，避免直接通过地址访问
            //查询分页数据
            HashMap<String, Object> map = superService.selectPage(user);
            //把数据传到前端
            m.addAttribute("info", map);
            m.addAttribute("convalue", user.getConValue());
            m.addAttribute("condition", user.getCondition());
            return "user/user-list";
        } else {
            return null;
        }

    }

    //打开修改页面
    @RequestMapping("/updatePage")
    public String edit(UserInfo user, ModelMap m) {
        //根据userId查询
        UserInfo u = superService.selectByUserId(user);
        //将查询结果返回给前端
        m.addAttribute("user", u);
        return "user/user-update";
    }

    //修改ajax请求
    @RequestMapping("/updateAjax")
    @ResponseBody
    public HashMap<String, Object> updateAjax(UserInfo user) {
        HashMap<String, Object> map = new HashMap<>();
        String info = superService.update(user);
        map.put("info", info);
        return map;
    }

    //删除ajax请求
    @RequestMapping("/deleteAjax")
    @ResponseBody
    public HashMap<String, Object> deleteAjax(UserInfo user) {
        HashMap<String, Object> map = new HashMap<>();
        String info = superService.delete(user);
        map.put("info", info);
        return map;
    }

    //导出excel
    @RequestMapping(value = "/userExcel", method = RequestMethod.GET)
    public void userExcel(HttpServletResponse response) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("用户信息表");
        List<UserInfo> list = superService.userExcel();
        String fileName = "AdminList" + ".xls";//设置导出文件的文件名

        //新增数据行，设置单元格数据
        int rowNum = 1;
        //headers表示excel的第一行的表头
        String[] headers = {"编号", "用户名", "密码", "权限等级", "头像地址", "职位", "邮箱"};
        HSSFRow row = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //将查询的数据放入对应的列
        for (UserInfo user : list) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(user.getUserId());
            row1.createCell(1).setCellValue(user.getUserName());
            row1.createCell(2).setCellValue(user.getUserPwd());
            row1.createCell(3).setCellValue(user.getUserLevel());
            row1.createCell(4).setCellValue(user.getUrl());
            row1.createCell(5).setCellValue(user.getUserPosition());
            row1.createCell(6).setCellValue(user.getUserEmail());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());

    }

    //打开添加页面
    @RequestMapping("/insertPage")
    public String insert() {

        return "user/user-insert";
    }

    //处理添加的ajax请求
    @RequestMapping("/insertAjax")
    @ResponseBody
    public HashMap<String, Object> insertAjax(UserInfo user) {
        HashMap<String, Object> map = new HashMap<>();
        String info = superService.insert(user);
        map.put("info", info);
        return map;
    }
}
