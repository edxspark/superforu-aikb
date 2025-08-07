package org.dromara.kb.kmCollaboration.service.impl;

import org.dromara.com.team.domain.ComTeam;
import org.dromara.com.team.mapper.ComTeamMapper;
import org.dromara.com.teamMate.domain.ComTeamMate;
import org.dromara.com.teamMate.mapper.ComTeamMateMapper;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.kb.km.domain.Km;
import org.dromara.kb.km.mapper.KmMapper;
import org.dromara.kb.kmShare.domain.KmShare;
import org.dromara.kb.kmShare.domain.bo.KmShareBo;
import org.dromara.kb.kmShare.domain.vo.KmShareVo;
import org.springframework.stereotype.Service;
import org.dromara.kb.kmCollaboration.domain.bo.KmCollaborationBo;
import org.dromara.kb.kmCollaboration.domain.vo.KmCollaborationVo;
import org.dromara.kb.kmCollaboration.domain.KmCollaboration;
import org.dromara.kb.kmCollaboration.mapper.KmCollaborationMapper;
import org.dromara.kb.kmCollaboration.service.IKmCollaborationService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 协同管理Service业务层处理
 *
 * @author zzg
 * @date 2023-12-08
 */
@RequiredArgsConstructor
@Service
public class KmCollaborationServiceImpl implements IKmCollaborationService {

    private final KmCollaborationMapper baseMapper;

    private final ComTeamMapper comTeamMapper;

    /**
     * 查询协同管理
     */
    @Override
    public KmCollaborationVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询协同管理列表
     */
    @Override
    public TableDataInfo<KmCollaborationVo> queryPageList(KmCollaborationBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<KmCollaboration> lqw = buildQueryWrapper(bo);
        Page<KmCollaborationVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询协同管理列表
     */
    @Override
    public List<KmCollaborationVo> queryList(KmCollaborationBo bo) {
        LambdaQueryWrapper<KmCollaboration> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<KmCollaboration> buildQueryWrapper(KmCollaborationBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<KmCollaboration> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getLinkKmId() != null, KmCollaboration::getLinkKmId, bo.getLinkKmId());
        return lqw;
    }

    /**
     * 新增协同管理
     * 1. 检查
     * 2. 新增协同记录
     * @author moks.mo
     */
    @Override
    public R<KmCollaborationVo> insertByBo(KmCollaborationBo bo) {
        KmCollaboration add = MapstructUtils.convert(bo, KmCollaboration.class);
        // 1. 检查
        validEntityBeforeSave(add);

        long count = baseMapper.selectCount(new LambdaQueryWrapper<KmCollaboration>()
            .eq(KmCollaboration::getLinkKmId,bo.getLinkKmId())
            .eq(KmCollaboration::getLinkTeamId,bo.getLinkTeamId()));
        if(count>0){
            KmCollaborationVo kmCollaborationVo = MapstructUtils.convert(add,KmCollaborationVo.class);
            return R.fail(kmCollaborationVo);
        }

        // 2. 新增协同记录
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        KmCollaborationVo kmCollaborationVo = MapstructUtils.convert(add,KmCollaborationVo.class);
        return R.ok(kmCollaborationVo);
    }

    /**
     * 修改协同管理
     */
    @Override
    public Boolean updateByBo(KmCollaborationBo bo) {
        KmCollaboration update = MapstructUtils.convert(bo, KmCollaboration.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(KmCollaboration entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除协同管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 据知识库ID获取团队协助集合信息
     * @param linkKmId 知识库id
     * @author zzg
     */
    @Override
    public List<KmCollaborationVo> listByLinkKmId(Long linkKmId) {
        KmCollaborationBo bo = new KmCollaborationBo();
        bo.setLinkKmId(linkKmId);
        LambdaQueryWrapper<KmCollaboration> lqw = buildQueryWrapper(bo);
        List<KmCollaborationVo> list = baseMapper.selectVoList(lqw);

        // 渲染返回信息
        for(KmCollaborationVo kmCollaborationVo:list){
            ComTeam teamMate = comTeamMapper.selectById(kmCollaborationVo.getLinkTeamId());
            kmCollaborationVo.setLinkTeamName(teamMate.getTeamName());
        }

        return list;
    }
}
