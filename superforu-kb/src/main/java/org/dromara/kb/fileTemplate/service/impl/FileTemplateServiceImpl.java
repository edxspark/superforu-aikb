package org.dromara.kb.fileTemplate.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.kb.fileTemplateType.domain.FileTemplateType;
import org.dromara.kb.fileTemplateType.domain.vo.FileTemplateTypeVo;
import org.dromara.kb.fileTemplateType.mapper.FileTemplateTypeMapper;
import org.dromara.kb.folderFile.domain.FolderFile;
import org.dromara.kb.folderFile.domain.bo.FolderFileBo;
import org.dromara.kb.folderFile.domain.bo.FolderFileRenameBo;
import org.dromara.kb.folderFile.service.impl.FolderFileServiceImpl;
import org.dromara.system.domain.SysOss;
import org.dromara.system.domain.vo.SysOssVo;
import org.dromara.system.mapper.SysOssMapper;
import org.dromara.system.service.ISysConfigService;
import org.springframework.stereotype.Service;
import org.dromara.kb.fileTemplate.domain.bo.FileTemplateBo;
import org.dromara.kb.fileTemplate.domain.vo.FileTemplateVo;
import org.dromara.kb.fileTemplate.domain.FileTemplate;
import org.dromara.kb.fileTemplate.mapper.FileTemplateMapper;
import org.dromara.kb.fileTemplate.service.IFileTemplateService;

import java.util.*;

/**
 * 文档模板Service业务层处理
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@RequiredArgsConstructor
@Service
public class FileTemplateServiceImpl implements IFileTemplateService {

    private final FileTemplateMapper baseMapper;

    private final FileTemplateTypeMapper fileTemplateTypeMapper;

    private final FolderFileServiceImpl folderFileService;

    private final SysOssMapper sysOssMapper;

    /**
     * 查询文档模板
     */
    @Override
    public FileTemplateVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询文档模板列表
     */
    @Override
    public TableDataInfo<FileTemplateVo> queryPageList(FileTemplateBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FileTemplate> lqw = buildQueryWrapper(bo);
        Page<FileTemplateVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);

        // 判空处理
        if(result.getTotal()==0){
            return TableDataInfo.build(result);
        }

        // 填充模版分类名称
        List<Long> templateIdsBefore = new ArrayList<Long>();
        for(FileTemplateVo fileTemplateVo:result.getRecords()){
            templateIdsBefore.add(fileTemplateVo.getLinkFileTemplateTypeId());
        }
        List<Long> templateIds = new LinkedList<>(new TreeSet<>(templateIdsBefore));
        List<FileTemplateTypeVo> fileTemplateTypeVos = fileTemplateTypeMapper.selectVoList(new LambdaQueryWrapper<FileTemplateType>().in(FileTemplateType::getId, templateIds));
        for(FileTemplateVo fileTemplateVo:result.getRecords()){
            for (FileTemplateTypeVo fileTemplateTypeVo:fileTemplateTypeVos){

                if((""+fileTemplateVo.getLinkFileTemplateTypeId()).equals(""+fileTemplateTypeVo.getId())){
                    fileTemplateVo.setLinkFileTemplateTypeName(fileTemplateTypeVo.getName());
                    break;
                }
            }
        }

        // 填充编辑器URL
        String editorURL = SpringUtils.getBean(ISysConfigService.class).selectConfigByKey("com.editor.url");
        for(FileTemplateVo fileTemplateVo:result.getRecords()){
            fileTemplateVo.setEditorURL(editorURL);
        }

        // 填充封面
        String fileServerURL = SpringUtils.getBean(ISysConfigService.class).selectConfigByKey("com.file.server.url");
        List<Long> ossIdsBefore = new ArrayList<Long>();
        for(FileTemplateVo vo:result.getRecords()){
            ossIdsBefore.add(Long.parseLong(vo.getPicUrl().equals("")?"0":vo.getPicUrl()));
        }
        List<Long> ossIds = new LinkedList<>(new TreeSet<>(ossIdsBefore));
        List<SysOssVo> ossList = sysOssMapper.selectVoList(new LambdaQueryWrapper<SysOss>().in(SysOss::getOssId, ossIds));
        for(FileTemplateVo fileTemplateVo:result.getRecords()){
            for(SysOssVo sysOssVo:ossList){
                if(fileTemplateVo.getPicUrl().equals(""+sysOssVo.getOssId())){
                    fileTemplateVo.setPicUrl(fileServerURL+sysOssVo.getFileName());
                    break;
                }
            }
        }

        return TableDataInfo.build(result);
    }

    /**
     * 查询文档模板列表
     */
    @Override
    public List<FileTemplateVo> queryList(FileTemplateBo bo) {
        LambdaQueryWrapper<FileTemplate> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FileTemplate> buildQueryWrapper(FileTemplateBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FileTemplate> lqw = Wrappers.lambdaQuery();
        lqw.eq((null != bo.getLinkFileTemplateTypeId() && StringUtils.isNotBlank(""+bo.getLinkFileTemplateTypeId())), FileTemplate::getLinkFileTemplateTypeId, bo.getLinkFileTemplateTypeId());
        lqw.eq(StringUtils.isNotBlank(bo.getFileTypeCode()), FileTemplate::getFileTypeCode, bo.getFileTypeCode());
        lqw.eq(StringUtils.isNotBlank(bo.getName()), FileTemplate::getName, bo.getName());
        lqw.orderByDesc(FileTemplate::getUpdateTime);
        return lqw;
    }

    /**
     * 新增文档模板
     * 1. 创建文档记录
     * 2. 创建模版记录
     */
    @Override
    public Boolean insertByBo(FileTemplateBo bo) {
        FileTemplate add = MapstructUtils.convert(bo, FileTemplate.class);
        validEntityBeforeSave(add);

        // 1. 创建文档记录
        FolderFileBo folderFileBo = new FolderFileBo();
        folderFileBo.setFileName(add.getName());
        folderFileBo.setLinkFileTypeCode(bo.getFileTypeCode());
        folderFileBo.setLinkFileTypeName(bo.getFileTypeName());
        folderFileBo.setParentId(0L);
        String platformKmId = SpringUtils.getBean(ISysConfigService.class).selectConfigByKey("km.platform.template.km.id");
        folderFileBo.setLinkKmId(Long.parseLong(platformKmId));
        folderFileService.insertByBo(folderFileBo);

        // 2. 创建模版记录
        // 保存文档ID到模版记录
        add.setAttrContent(""+folderFileBo.getId());
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改文档模板
     */
    @Override
    public Boolean updateByBo(FileTemplateBo bo) {
        FileTemplate update = MapstructUtils.convert(bo, FileTemplate.class);
        validEntityBeforeSave(update);

        // 更新模版名称到文档
        if(null != update.getAttrContent()){
            FolderFileRenameBo folderFileRenameBo = new FolderFileRenameBo();
            folderFileRenameBo.setId(Long.parseLong(update.getAttrContent()));
            folderFileRenameBo.setFileName(update.getName());
            folderFileService.updateByBo(folderFileRenameBo);
        }

        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FileTemplate entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除文档模板
     * 1. 删除文档
     * 2. 删除模版记录
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {

        // 1. 删除文档
        for(Long id:ids){
            FileTemplateVo fileTemplateVo = baseMapper.selectVoById(id);
            if(null != fileTemplateVo){
                List<Long> delFileIds = new ArrayList<>();
                delFileIds.add(Long.parseLong(fileTemplateVo.getAttrContent()));
                folderFileService.deleteWithValidByIds(delFileIds,true,null);
            }
        }

        // 2. 删除模版记录
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
