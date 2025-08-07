package org.dromara.kb.fileRecently.service.impl;

import org.dromara.com.user.domain.ComUser;
import org.dromara.com.user.domain.vo.ComUserVo;
import org.dromara.com.user.mapper.ComUserMapper;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.kb.fileContent.mapper.FileContentMapper;
import org.dromara.kb.folderFile.mapper.FolderFileMapper;
import org.dromara.kb.utils.common.FileDateUtil;
import org.springframework.stereotype.Service;
import org.dromara.kb.fileRecently.domain.bo.FileRecentlyBo;
import org.dromara.kb.fileRecently.domain.vo.FileRecentlyVo;
import org.dromara.kb.fileRecently.domain.FileRecently;
import org.dromara.kb.fileRecently.mapper.FileRecentlyMapper;
import org.dromara.kb.fileRecently.service.IFileRecentlyService;

import java.util.*;

/**
 * 最近编辑Service业务层处理
 *
 * @author Lion Li
 * @date 2023-12-21
 */
@RequiredArgsConstructor
@Service
public class FileRecentlyServiceImpl implements IFileRecentlyService {

    private final FileRecentlyMapper baseMapper;
    private final FolderFileMapper folderFileMapper;
    private final FileContentMapper fileContentMapper;
    private final ComUserMapper comUserMapper;

    /**
     * 查询最近编辑
     */
    @Override
    public FileRecentlyVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询最近编辑列表
     * 1. 查询最近编辑记录
     * 2. 内容处理
     * @author moks.mo
     */
    @Override
    public TableDataInfo<FileRecentlyVo> queryPageList(FileRecentlyBo bo, PageQuery pageQuery) {

        // 1. 查询最近编辑记录
        bo.setLinkUserId(LoginHelper.getLoginUser().getUserId());
        LambdaQueryWrapper<FileRecently> lqw = buildQueryWrapper(bo);
        Page<FileRecentlyVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        if(result.getTotal()==0){
            return TableDataInfo.build(result);
        }

        // 2. 内容处理
        List<FileRecentlyVo> fileRecentlyVoList =  result.getRecords();
        for(FileRecentlyVo pageFileRecentlyVo:fileRecentlyVoList){
            pageFileRecentlyVo.setEditorTime(FileDateUtil.toEditDate(pageFileRecentlyVo.getCreateTime()));
        }

        // 填充创建人和修改人
        // 获取用户信息列表
        List<Long> userIdsBefore = new ArrayList<Long>();
        for(FileRecentlyVo vo:fileRecentlyVoList){
            userIdsBefore.add(Long.parseLong(vo.getCreateBy()));
            userIdsBefore.add(Long.parseLong(StringUtils.isEmpty(vo.getUpdateBy())?"0":vo.getUpdateBy()));
        }

        List<Long> userIds = new LinkedList<>(new TreeSet<>(userIdsBefore));
        List<ComUserVo> comUserList = comUserMapper.selectVoList(new LambdaQueryWrapper<ComUser>().in(ComUser::getId, userIds));

        for(FileRecentlyVo vo:fileRecentlyVoList){
            for(ComUserVo comUserVo:comUserList){
                // 创建人
                if(vo.getCreateBy().equals(""+comUserVo.getId())){
                    vo.setCreateBy(comUserVo.getUserName());
                }

                // 修改人
                if(StringUtils.isNotEmpty(vo.getUpdateBy()) && vo.getUpdateBy().equals(""+comUserVo.getId())){
                    vo.setUpdateBy(comUserVo.getUserName());
                }
            }
        }

        return TableDataInfo.build(result);
    }

    /**
     * 查询最近编辑列表
     */
    @Override
    public List<FileRecentlyVo> queryList(FileRecentlyBo bo) {
        LambdaQueryWrapper<FileRecently> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FileRecently> buildQueryWrapper(FileRecentlyBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FileRecently> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getLinkUserId() != null, FileRecently::getLinkUserId, bo.getLinkUserId());
        return lqw;
    }

    /**
     * 新增最近编辑
     */
    @Override
    public Boolean insertByBo(FileRecentlyBo bo) {
        FileRecently add = MapstructUtils.convert(bo, FileRecently.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改最近编辑
     */
    @Override
    public Boolean updateByBo(FileRecentlyBo bo) {
        FileRecently update = MapstructUtils.convert(bo, FileRecently.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FileRecently entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除最近编辑
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
