package com.ecut.controller;

import com.alibaba.fastjson.JSONObject;
import com.ecut.mapper.SubjectScoreMapper;
import com.ecut.model.SubjectStudentDO;
import com.ecut.service.SubjectStudentService;
import com.ecut.util.CommonResult;
import com.ecut.vo.ResultVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhouwei
 * @since 2022-06-25
 */
@RestController
@RequestMapping("/subject-student")
public class SubjectStudentController {
    @Resource
    SubjectScoreMapper subjectScoreMapper;
    @Resource
    SubjectStudentService subjectStudentService;

    /**
     * 处理图片上传
     *
     * @param id
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/file/upload/{id}")
    public JSONObject fileUpload(@PathVariable("id") Integer id, @RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request) throws IOException {
        //上传路径保存设置
        //获得SpringBoot当前项目的路径：System.getProperty("user.dir")
        //保存的linux系统的目录
        String path = "/home/project/pic/";
        File realPath = new File(path);
        if (!realPath.exists()) {
            realPath.mkdirs();
        }
        //上传文件地址
        System.out.println("上传文件保存地址：" + realPath);
        //解决文件名字问题：我们使用uuid;
        String filename = "pg-" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
        File newfile = new File(realPath, filename);
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(newfile);
        String url = "http://1.117.87.146:8233/pictures/" + filename;
        subjectScoreMapper.updatePic(id, url);
        //给editormd进行回调
        JSONObject res = new JSONObject();
        res.put("url", url);
        res.put("success", 1);
        res.put("message", "upload success!");
        return res;
    }


    @GetMapping("/redis-cache")
    public ResultVo redisCache() throws JsonProcessingException {
        List<SubjectStudentDO> list = subjectStudentService.allList();
        if (null == list) {
            return CommonResult.fail();
        } else {
            return CommonResult.success(list);
        }
    }
}

