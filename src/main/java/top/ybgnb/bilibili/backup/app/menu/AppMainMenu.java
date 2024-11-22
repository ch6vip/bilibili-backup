package top.ybgnb.bilibili.backup.app.menu;

import lombok.extern.slf4j.Slf4j;
import top.ybgnb.bilibili.backup.app.business.BusinessRunner;
import top.ybgnb.bilibili.backup.app.business.BusinessType;
import top.ybgnb.bilibili.backup.app.utils.MenuUtil;
import top.ybgnb.bilibili.backup.biliapi.error.BusinessException;

import java.util.Scanner;

/**
 * @ClassName AppMainMenu
 * @Description app主菜单
 * @Author hzhilong
 * @Time 2024/11/22
 * @Version 1.0
 */
@Slf4j
public class AppMainMenu {

    /**
     * 选择业务
     */
    public static void chooseBusiness(Scanner sc) {
        do {
            log.info("请选择对应功能的序号:");
            BusinessType[] businessTypes = BusinessType.values();
            for (int i = 0; i < businessTypes.length; i++) {
                log.info("{}: {}", i, businessTypes[i].getName());
            }

            int pos = MenuUtil.checkInputPos(businessTypes.length, sc.nextLine());
            // 输入是否有效
            if (pos > -1) {
                // 判断是否退出
                BusinessType businessType = businessTypes[pos];
                if (BusinessType.EXIT.equals(businessType)) {
                    return;
                }
                // 执行业务
                try {
                    BusinessRunner.processBusiness(businessType, sc);
                } catch (BusinessException ignored) {

                }
            }
        } while (true);
    }


}