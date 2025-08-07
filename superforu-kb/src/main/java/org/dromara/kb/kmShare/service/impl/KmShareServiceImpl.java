package org.dromara.kb.kmShare.service.impl;

import cn.hutool.core.lang.UUID;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.kb.utils.common.ErrorMsg;
import org.dromara.system.service.ISysConfigService;
import org.springframework.stereotype.Service;
import org.dromara.kb.kmShare.domain.bo.KmShareBo;
import org.dromara.kb.kmShare.domain.vo.KmShareVo;
import org.dromara.kb.kmShare.domain.KmShare;
import org.dromara.kb.kmShare.mapper.KmShareMapper;
import org.dromara.kb.kmShare.service.IKmShareService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 分享预览Service业务层处理
 *
 * @author zzg
 * @date 2023-12-08
 */
@RequiredArgsConstructor
@Service
public class KmShareServiceImpl implements IKmShareService {

    private final KmShareMapper baseMapper;

    /**
     * 查询分享预览
     */
    @Override
    public KmShareVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询分享预览
     */
    @Override
    public KmShareVo queryByKmShareId(String shareId)
    {

        LambdaQueryWrapper<KmShare> lqw = Wrappers.lambdaQuery();
        lqw.eq(shareId != null, KmShare::getShareId, shareId);
        KmShareVo kmShareVo = baseMapper.selectVoOne(lqw);

        // 填充文件内容

        // 填充编辑器路径
        String editorURL = SpringUtils.getBean(ISysConfigService.class).selectConfigByKey("kb.editor.preview.url");
        kmShareVo.setEditorURL(editorURL);

        return kmShareVo;
    }


    /**
     * 查询分享预览列表
     */
    @Override
    public TableDataInfo<KmShareVo> queryPageList(KmShareBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<KmShare> lqw = buildQueryWrapper(bo);
        Page<KmShareVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询分享预览列表
     */
    @Override
    public List<KmShareVo> queryList(KmShareBo bo) {
        LambdaQueryWrapper<KmShare> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<KmShare> buildQueryWrapper(KmShareBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<KmShare> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getLinkKmId() != null, KmShare::getLinkKmId, bo.getLinkKmId());
        return lqw;
    }

    /**
     * 新增分享预览
     *
     * 1. 重复检查
     * 2. 生成分享ID
     * 3. 返回分享数据
     */
    @Override
    public R<KmShareVo> insertByBo(KmShareBo bo) {
        KmShare add = MapstructUtils.convert(bo, KmShare.class);

        // 1. 重复检查
        KmShareBo queryKmSharebo = new KmShareBo();
        queryKmSharebo.setLinkKmId(bo.getLinkKmId());
        LambdaQueryWrapper<KmShare> lqw = buildQueryWrapper(queryKmSharebo);
        KmShareVo kmShareRepeatVo = baseMapper.selectVoOne(lqw);
        if(null !=kmShareRepeatVo){
            baseMapper.deleteById(kmShareRepeatVo.getId());
        }


        // 2. 生成分享ID
        String shareId = UUID.fastUUID().toString(true);
        add.setShareId(shareId);


        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }

        // 3. 返回分享数据
        KmShareVo kmShareVo = MapstructUtils.convert(add,KmShareVo.class);

        // 获取分享参数：系统域名、分享路径
        String sharePath = SpringUtils.getBean(ISysConfigService.class).selectConfigByKey("kb.km.share.path");
        kmShareVo.setShareURL(sharePath+"?id="+shareId);

        return R.ok(kmShareVo);
    }

    /**
     * 修改分享预览
     */
    @Override
    public Boolean updateByBo(KmShareBo bo) {
        KmShare update = MapstructUtils.convert(bo, KmShare.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(KmShare entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除分享预览
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 根据知识库ID获取分享信息
     * @param linkKmId 知识库ID
     * @author zzg
     */
    @Override
    public KmShareVo getOneByLinkKmId(Long linkKmId) {
        KmShareBo bo = new KmShareBo();
        bo.setLinkKmId(linkKmId);
        LambdaQueryWrapper<KmShare> lqw = buildQueryWrapper(bo);
        KmShareVo kmShareVo = baseMapper.selectVoOne(lqw);

        if(null == kmShareVo){
            return kmShareVo;
        }

        String sharePath = SpringUtils.getBean(ISysConfigService.class).selectConfigByKey("kb.km.share.path");
        kmShareVo.setShareURL(sharePath+"?id="+kmShareVo.getShareId());

        return kmShareVo;
    }
}
