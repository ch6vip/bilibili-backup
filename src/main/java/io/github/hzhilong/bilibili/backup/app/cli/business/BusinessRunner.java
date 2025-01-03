package io.github.hzhilong.bilibili.backup.app.cli.business;

import lombok.extern.slf4j.Slf4j;
import io.github.hzhilong.bilibili.backup.api.bean.Upper;
import io.github.hzhilong.base.error.BusinessException;

import java.util.Scanner;

/**
 * 业务执行器
 *
 * @author hzhilong
 * @version 1.0
 */
@Slf4j
public class BusinessRunner {

    /**
     * 处理业务
     *
     * @param businessType 业务类型
     * @return 当前登录的UP信息
     */
    public static Upper processBusiness(CLIBusinessType businessType, Scanner scanner) throws BusinessException {
        log.info("================【{}】================", businessType.getName());
        try {
            // 获取业务类
            Class<? extends BaseBusiness> businessClass = businessType.getBusinessClass();
            if (businessClass == null) {
                // 退出
                return null;
            }
            // 实例化业务类
            BaseBusiness business = businessClass.getDeclaredConstructor().newInstance();
            // 处理业务
            Upper upper = business.process(scanner);
            if (upper != null) {
                log.info("【{}】处理成功，当前账号：{}\n", businessType.getName(), upper.getName());
            }
            Thread.sleep(2000);
            return upper;
        } catch (BusinessException ex) {
            log.info("【{}】处理失败，{}\n", businessType.getName(), ex.getMessage());
            throw ex;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("内部错误");
        }
    }

}
