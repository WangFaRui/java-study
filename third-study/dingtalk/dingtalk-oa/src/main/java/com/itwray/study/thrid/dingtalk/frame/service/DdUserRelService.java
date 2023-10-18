package com.itwray.study.thrid.dingtalk.frame.service;

import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.itwray.study.thrid.dingtalk.frame.client.DingTalkUserInfoClient;
import com.itwray.study.thrid.dingtalk.frame.dao.DdUserRelDao;
import com.itwray.study.thrid.dingtalk.frame.model.ApprovalFormInstance;
import com.itwray.study.thrid.dingtalk.frame.model.business.UserResponse;
import com.itwray.study.thrid.dingtalk.frame.model.entity.DdUserRel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 钉钉用户关联表 服务实现类
 * </p>
 *
 * @author wangfarui
 * @since 2023-08-02
 */
@Service
public class DdUserRelService {

    @Resource
    private DdUserRelDao ddUserRelDao;

    @Transactional(rollbackFor = Exception.class)
    public DdUserRel resolveDdUserRel(ApprovalFormInstance instance) {
        // 查询已关联的用户信息
        DdUserRel ddUserRel = ddUserRelDao.lambdaQuery()
                .eq(DdUserRel::getTenantId, instance.getTenantId())
                .eq(DdUserRel::getScmUserId, instance.getUserId())
                .last("limit 1")
                .one();
        if (ddUserRel != null) {
            return ddUserRel;
        }
        // 通过业务用户信息 创建钉钉用户关联数据
        return createDdUserRelByBusinessUser(instance);
    }

    @Transactional(rollbackFor = Exception.class)
    public DdUserRel resolveDdUserRel(Long tenantId, String ddUserId) {
        DdUserRel ddUserRel = ddUserRelDao.lambdaQuery()
                .eq(DdUserRel::getTenantId, tenantId)
                .eq(DdUserRel::getDdUserId, ddUserId)
                .last("limit 1")
                .one();
        if (ddUserRel != null) {
            return ddUserRel;
        }

        return createDdUserRelByDdUser(tenantId, ddUserId);
    }

    private DdUserRel createDdUserRelByBusinessUser(ApprovalFormInstance dto) {
        // TODO 根据业务系统用户id查询用户详情
        UserResponse userResponse = new UserResponse();

        // 通过手机号码查询钉钉用户id
        String ddUserId = DingTalkUserInfoClient.getUserIdByMobile(userResponse.getMobile());
        // 通过钉钉用户id查询钉钉用户详情
        OapiV2UserGetResponse.UserGetResponse ddUserRsp = DingTalkUserInfoClient.getUserById(ddUserId);

        // 新增关联对象
        DdUserRel newEntity = new DdUserRel();
        newEntity.setTenantId(dto.getTenantId());
        newEntity.setScmUserId(dto.getUserId());
        newEntity.setDdUserId(ddUserId);
        newEntity.setDdUserName(userResponse.getUserName());
        newEntity.setUserMobile(userResponse.getMobile());
        newEntity.setDdDeptId(ddUserRsp.getDeptIdList().get(0));
        ddUserRelDao.save(newEntity);

        return newEntity;
    }

    /**
     * 通过钉钉用户创建关联信息
     */
    private DdUserRel createDdUserRelByDdUser(Long tenantId, String ddUserId) {
        OapiV2UserGetResponse.UserGetResponse ddUserRsp = DingTalkUserInfoClient.getUserById(ddUserId);
        if (StringUtils.isBlank(ddUserRsp.getMobile())) {
            return null;
        }
        // TODO 根据手机号查询业务系统的用户详情
        UserResponse userResponse = new UserResponse();
        if (userResponse == null) {
            return null;
        }

        // 新增关联对象
        DdUserRel newEntity = new DdUserRel();
        newEntity.setTenantId(tenantId);
        newEntity.setScmUserId(userResponse.getId());
        newEntity.setDdUserId(ddUserId);
        newEntity.setDdUserName(userResponse.getUserName());
        newEntity.setUserMobile(ddUserRsp.getMobile());
        newEntity.setDdDeptId(ddUserRsp.getDeptIdList().get(0));
        ddUserRelDao.save(newEntity);

        return newEntity;
    }

}
