package org.dromara.com.quickEntranceConfig.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.dromara.com.quickEntranceConfig.domain.bo.ComQuickEntranceConfigAddBo;
import org.dromara.com.quickEntranceConfig.domain.bo.ComQuickEntranceConfigEditBo;
import org.dromara.com.quickEntranceConfig.domain.vo.FileTypeDictDataVo;
import org.dromara.com.utils.common.ErrorMsg;
import org.dromara.com.utils.enums.InitializationStatus;
import org.dromara.common.core.exception.base.BaseException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.dromara.system.domain.vo.SysDictDataVo;
import org.dromara.system.service.ISysDictDataService;
import org.springframework.stereotype.Service;
import org.dromara.com.quickEntranceConfig.domain.vo.ComQuickEntranceConfigVo;
import org.dromara.com.quickEntranceConfig.domain.ComQuickEntranceConfig;
import org.dromara.com.quickEntranceConfig.mapper.ComQuickEntranceConfigMapper;
import org.dromara.com.quickEntranceConfig.service.IComQuickEntranceConfigService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 快捷入口配置Service业务层处理
 *
 * @author JackLiao
 * @date 2023-12-11
 */
@RequiredArgsConstructor
@Service
public class ComQuickEntranceConfigServiceImpl implements IComQuickEntranceConfigService {

    private final ISysDictDataService sysDictDataService;

    private final ComQuickEntranceConfigMapper baseMapper;


    /**
     * 查询快捷入口配置列表
     */
    @Override
    public TableDataInfo<ComQuickEntranceConfigVo> queryPageList(PageQuery pageQuery) {
        Page<ComQuickEntranceConfigVo> result = baseMapper.selectVoPage(pageQuery.build(), null);

        List<ComQuickEntranceConfigVo> records = result.getRecords();

        if (CollUtil.isEmpty(records)) {
            return TableDataInfo.build(result);
        }
        //合并数据
        merge(records);

        return TableDataInfo.build(result);
    }

    /**
     * 查询已初始化的快捷入口配置列表
     */
    @Override
    public String queryInitializationList() {
        List<ComQuickEntranceConfigVo> result = baseMapper.selectVoList(new LambdaQueryWrapper<ComQuickEntranceConfig>()
            .eq(ComQuickEntranceConfig::getStatus, InitializationStatus.INITIALIZED.getValue()));
        return result.stream()
            .map(vo -> String.valueOf(vo.getId()))
            .collect(Collectors.joining(","));
    }


    /**
     * 新增快捷入口配置
     */
    @Override
    public Boolean insertByBo(ComQuickEntranceConfigAddBo bo) {

        //查询该文件id是否已存在
        ComQuickEntranceConfigVo comQuickEntranceConfigVo = baseMapper.selectVoOne(new LambdaQueryWrapper<ComQuickEntranceConfig>().eq(ComQuickEntranceConfig::getLinkFileTypeId, bo.getLinkFileTypeId()));
        if (ObjectUtil.isNotNull(comQuickEntranceConfigVo)) {
            throw new BaseException(ErrorMsg.ERR_COM_FILE_TYPE_EXIST.getMessage());
        }
        ComQuickEntranceConfig add = MapstructUtils.convert(bo, ComQuickEntranceConfig.class);
        return baseMapper.insert(add) > 0;
    }

    /**
     * 修改快捷入口配置
     */
    @Override
    public Boolean updateByBo(ComQuickEntranceConfigEditBo bo) {
        ComQuickEntranceConfig update = MapstructUtils.convert(bo, ComQuickEntranceConfig.class);
        return baseMapper.updateById(update) > 0;
    }

    @Override
    public List<ComQuickEntranceConfigVo> queryUserQuickEntranceConfigList(List<String> ids) {
        List<ComQuickEntranceConfigVo> comQuickEntranceConfigVos = baseMapper.selectVoList(new LambdaQueryWrapper<ComQuickEntranceConfig>()
            .in(CollUtil.isNotEmpty(ids),ComQuickEntranceConfig::getId, ids));
        //合并数据
        merge(comQuickEntranceConfigVos);
        return comQuickEntranceConfigVos;
    }

    /**
     * 根据快捷入口配置信息组合文件类型信息
     *
     * @param records
     */
    public void merge(List<ComQuickEntranceConfigVo> records){
        List<Long> collect = records.stream().map(ComQuickEntranceConfigVo::getLinkFileTypeId).toList();


        //查询文件类型字典数据
        List<SysDictDataVo> sysDictDataVos = sysDictDataService.selectDictDataList(collect);

        Map<Long, SysDictDataVo> fileTypeVoMap = sysDictDataVos.stream()
            .collect(Collectors.toMap(SysDictDataVo::getDictCode, Function.identity()));

        records.forEach(quickEntranceConfigVo -> {
            SysDictDataVo fileTypeVo = fileTypeVoMap.get(quickEntranceConfigVo.getLinkFileTypeId());
            String remark = fileTypeVo.getRemark();

            //将string字符串转为实体对象
            JSONObject jsonObject = JSONUtil.parseObj(remark);
            FileTypeDictDataVo bean = jsonObject.toBean(FileTypeDictDataVo.class);

            quickEntranceConfigVo.setName(fileTypeVo.getDictLabel());
            quickEntranceConfigVo.setIcon(bean.getIcon());
            quickEntranceConfigVo.setColor(bean.getColor());
            quickEntranceConfigVo.setAttrType(bean.getAttrType());
            quickEntranceConfigVo.setCode(fileTypeVo.getDictValue());
        });
    }



}
