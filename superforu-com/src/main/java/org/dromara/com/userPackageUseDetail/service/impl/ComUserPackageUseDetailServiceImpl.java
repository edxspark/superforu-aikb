package org.dromara.com.userPackageUseDetail.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.dromara.com.userPackageDetail.domain.ComUserPackageDetail;
import org.dromara.com.userPackageDetail.mapper.ComUserPackageDetailMapper;
import org.dromara.com.userPackagePurchase.domain.vo.ComUserPackagePurchaseItems;
import org.dromara.com.userPackageUseDetail.domain.bo.AddUseDetailBo;
import org.dromara.com.userPackageUseDetail.domain.vo.ComAddUserPackageUseDetailVo;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.system.domain.bo.SysDictDataBo;
import org.dromara.system.domain.vo.SysDictDataVo;
import org.dromara.system.service.ISysDictDataService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.dromara.com.userPackageUseDetail.domain.bo.ComUserPackageUseDetailBo;
import org.dromara.com.userPackageUseDetail.domain.vo.ComUserPackageUseDetailVo;
import org.dromara.com.userPackageUseDetail.domain.ComUserPackageUseDetail;
import org.dromara.com.userPackageUseDetail.mapper.ComUserPackageUseDetailMapper;
import org.dromara.com.userPackageUseDetail.service.IComUserPackageUseDetailService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 用户充值消费明细Service业务层处理
 *
 * @author Lion Li
 * @date 2024-03-21
 */
@EnableAsync
@RequiredArgsConstructor
@Service
public class ComUserPackageUseDetailServiceImpl implements IComUserPackageUseDetailService {

    private final ComUserPackageUseDetailMapper baseMapper;
    private final ComUserPackageDetailMapper comUserPackageDetailMapper;
    private final ISysDictDataService iSysDictDataService;

    /**
     * 查询用户充值消费明细
     */
    @Override
    public ComUserPackageUseDetailVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询用户充值消费明细列表
     */
    @Override
    public TableDataInfo<ComUserPackageUseDetailVo> queryPageList(ComUserPackageUseDetailBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ComUserPackageUseDetail> lqw = buildQueryWrapper(bo);
        Page<ComUserPackageUseDetailVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询用户充值消费明细列表
     */
    @Override
    public List<ComUserPackageUseDetailVo> queryList(ComUserPackageUseDetailBo bo) {
        LambdaQueryWrapper<ComUserPackageUseDetail> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ComUserPackageUseDetail> buildQueryWrapper(ComUserPackageUseDetailBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ComUserPackageUseDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getLinkUserId() != null, ComUserPackageUseDetail::getLinkUserId, bo.getLinkUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getPackageCode()), ComUserPackageUseDetail::getPackageCode, bo.getPackageCode());
        lqw.like(StringUtils.isNotBlank(bo.getPackageName()), ComUserPackageUseDetail::getPackageName, bo.getPackageName());
        return lqw;
    }

    /**
     * 新增用户充值消费明细
     */
    @Override
    public Boolean insertByBo(ComUserPackageUseDetailBo bo) {
        ComUserPackageUseDetail add = MapstructUtils.convert(bo, ComUserPackageUseDetail.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 新增用户充值消费明细（异步执行）
     */
    @Transactional
    @Override
    public ComAddUserPackageUseDetailVo addUseDetailByBo(AddUseDetailBo bo) {
        ComAddUserPackageUseDetailVo rtComAddUserPackageUseDetailVo = new ComAddUserPackageUseDetailVo();

        // 检查参数
        // 消费必须大于0
        if(bo.getNumber()<=0){
            rtComAddUserPackageUseDetailVo.setStatus(300);
            rtComAddUserPackageUseDetailVo.setMsg("消费数量不能小于0！");
            return rtComAddUserPackageUseDetailVo;
        }

        // 调用处理（异步）
        asyncAddUseDetailByBo(bo,rtComAddUserPackageUseDetailVo);

        // 返回数据
        return rtComAddUserPackageUseDetailVo;
    }

    /**
     * 1. 获取消费套餐信息
     * 2. 计算消费信息
     * 3. 插入消费记录
     * 4. 更新用户套餐信息
     * */
    @Transactional
    public void asyncAddUseDetailByBo(AddUseDetailBo bo,ComAddUserPackageUseDetailVo rtComAddUserPackageUseDetailVo){

        // 获取当前登陆用户ID
        Long userId = LoginHelper.getLoginUser().getUserId();

        // 1. 获取消费套餐信息
        // 用户套餐信息
        LambdaQueryWrapper<ComUserPackageDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getPackageCode()), ComUserPackageDetail::getPackageCode, bo.getPackageCode());
        ComUserPackageDetail comUserPackageDetail = comUserPackageDetailMapper.selectOne(lqw);

        // 套餐列表信息
        String packageName = "";
        SysDictDataBo dictDataFileTypeBo = new SysDictDataBo();
        dictDataFileTypeBo.setDictType("com_user_package");
        List<SysDictDataVo> dictList = iSysDictDataService.selectDictDataList(dictDataFileTypeBo);
        for(SysDictDataVo sysDictDataVo:dictList){
            if(bo.getPackageCode().equals(sysDictDataVo.getDictValue())){
                packageName = sysDictDataVo.getDictLabel();
                break;
            }
        }

        // 2. 计算消费信息
        String appValue = comUserPackageDetail.getAppValue();
        float newAppValue = Long.parseLong(appValue)-bo.getNumber();

        // 3. 插入消费记录
        ComUserPackageUseDetail comUserPackageUseDetail = new ComUserPackageUseDetail();
        comUserPackageUseDetail.setLinkUserId(userId);
        comUserPackageUseDetail.setPackageCode(bo.getPackageCode());
        comUserPackageUseDetail.setPackageName(packageName);
        comUserPackageUseDetail.setBalanceBefore(Long.parseLong(appValue));
        comUserPackageUseDetail.setBalanceAfter((long)newAppValue);
        baseMapper.insert(comUserPackageUseDetail);

        // 4. 更新用户套餐信息
        comUserPackageDetail.setAppValue(""+((long)newAppValue));
        comUserPackageDetailMapper.updateById(comUserPackageDetail);

        // 返回值设置
        rtComAddUserPackageUseDetailVo.setStatus(200);
        rtComAddUserPackageUseDetailVo.setBalance(newAppValue);
        rtComAddUserPackageUseDetailVo.setPackageCode(bo.getPackageCode());
    }


    /**
     * 修改用户充值消费明细
     */
    @Override
    public Boolean updateByBo(ComUserPackageUseDetailBo bo) {
        ComUserPackageUseDetail update = MapstructUtils.convert(bo, ComUserPackageUseDetail.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ComUserPackageUseDetail entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除用户充值消费明细
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
