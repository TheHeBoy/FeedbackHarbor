package cn.hh.harbor.module.infra.convert.file;

import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.module.infra.controller.admin.file.vo.file.FileRespVO;
import cn.hh.harbor.module.infra.dal.dataobject.file.FileDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileConvert {

    FileConvert INSTANCE = Mappers.getMapper(FileConvert.class);

    FileRespVO convert(FileDO bean);

    PageResult<FileRespVO> convertPage(PageResult<FileDO> page);

}
