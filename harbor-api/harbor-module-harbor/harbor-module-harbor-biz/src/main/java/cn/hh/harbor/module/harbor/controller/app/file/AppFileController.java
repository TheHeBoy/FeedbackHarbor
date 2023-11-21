package cn.hh.harbor.module.harbor.controller.app.file;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hh.harbor.framework.common.pojo.CommonResult;
import cn.hh.harbor.framework.operatelog.core.annotations.OperateLog;
import cn.hh.harbor.framework.security.core.annotations.PreAuthenticated;
import cn.hh.harbor.module.harbor.controller.app.file.vo.AppFileUploadReqVO;
import cn.hh.harbor.module.harbor.controller.app.file.vo.AppFileUploadsReqVO;
import cn.hh.harbor.module.infra.api.file.FileApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import static cn.hh.harbor.framework.common.pojo.CommonResult.error;
import static cn.hh.harbor.framework.common.pojo.CommonResult.success;
import static cn.hh.harbor.module.harbor.enums.ErrorCodeConstants.File_NOT_NULL;

@Tag(name = "App - 文件存储")
@RestController
@RequestMapping("/harbor/file")
@Validated
@Slf4j
public class AppFileController {

    @Resource
    private FileApi fileApi;

    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    @PreAuthenticated
    @OperateLog(logArgs = false)
    public CommonResult<String> uploadFile(@Valid AppFileUploadReqVO uploadReqVO) throws Exception {
        return success(fileApi.createFile(uploadReqVO.getFile().getBytes()));
    }

    @PostMapping("/uploads")
    @Operation(summary = "上传多个文件")
    @PreAuthenticated
    @OperateLog(logArgs = false)
    public CommonResult<List<String>> uploadFiles(@Valid AppFileUploadsReqVO uploadReqVO) throws Exception {
        ArrayList<String> files = new ArrayList<>();
        for (MultipartFile file : uploadReqVO.getFiles()) {
            files.add(fileApi.createFile(file.getBytes()));
        }
        return success(files);
    }
}
