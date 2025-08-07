package org.dromara.kb.km.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.kb.km.domain.bo.KmTreeDataBo;
import org.dromara.kb.km.domain.vo.KmTreeDataVo;
import org.dromara.system.service.ISysConfigService;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.kb.km.domain.vo.KmVo;
import org.dromara.kb.km.domain.bo.KmBo;
import org.dromara.kb.km.service.IKmService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 知识库
 *
 * @author zzg
 * @date 2023-12-07
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/kb/km")
public class KmController extends BaseController {

    private final IKmService kmService;

    /**
     * 获取开启AI的知识库列表
     */
    @SaCheckLogin
    @GetMapping("/list/isOpenAI")
    public R<List<KmVo>> listIsOpenAI() {
        return R.ok(kmService.listIsOpenAI());
    }

    /**
     * 查询知识库列表
     */
    @SaCheckLogin
    @GetMapping("/list")
    public TableDataInfo<KmVo> list(KmBo bo, PageQuery pageQuery) {
        return kmService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询主知识库ID
     */
    @SaCheckLogin
    @GetMapping("/getMainKmId")
    public R<Void> getMainKmId() {
        return R.ok(kmService.getMainKmId());
    }



    /**
     * 查询知识库列表
     */
    @SaCheckLogin
    @GetMapping("/listMyselfAndShare")
    public TableDataInfo<KmVo> listMyselfAndShare(KmBo bo, PageQuery pageQuery) {
        return kmService.queryPageList(bo, pageQuery);
    }


    /**
     * 获取知识库详细信息
     *
     * @param id 主键
     */
    @SaCheckLogin
    @GetMapping("/{id}")
    public R<KmVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(kmService.queryById(id));
    }

    /**
     * 获取字典参数
     *
     * @param key 主键
     */
    @SaIgnore
    @GetMapping("/getParam/{key}")
    public R<String> getParam(@NotNull(message = "主键不能为空")
                           @PathVariable String key) {
        R<String> r = new R<String>();
        r.setData(SpringUtils.getBean(ISysConfigService.class).selectConfigByKey(key));
        r.setCode(200);
        return r;
    }


    /**
     * 新增知识库
     */
    @SaCheckLogin
    @Log(title = "知识库", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<KmVo> add(@Validated(AddGroup.class) @RequestBody KmBo bo) {
        return kmService.insertByBo(bo);
    }

    /**
     * 修改知识库
     */
    @SaCheckLogin
    @Log(title = "知识库", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody KmBo bo) {
        return toAjax(kmService.updateByBo(bo));
    }

    /**
     * 删除知识库
     *
     * @param ids 主键串
     */
    @SaCheckLogin
    @Log(title = "知识库", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(kmService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 获取知识库列表（不分页）
     * @param bo 知识库检索对象
     * @author zzg
     */
    @SaCheckLogin
    @GetMapping("/listAll")
    public R listAll(KmBo bo) {
        List<KmVo> list = kmService.queryList(bo);
        return R.ok(list);
    }

    /**
     * 查询知识库文档目录树
     */
    @SaIgnore
    @GetMapping("/listTreeData")
    public R<KmTreeDataVo> listTreeData(KmTreeDataBo bo) {
        return kmService.queryTreeData(bo);
    }

    /**
     * 更新知识库文档目录树
     */
    @SaCheckLogin
    @PostMapping("/updateTreeData")
    public R<Void> updateTreeData(@Validated(EditGroup.class) @RequestBody KmTreeDataBo bo) {
        return kmService.updateTreeData(bo);
    }

}
