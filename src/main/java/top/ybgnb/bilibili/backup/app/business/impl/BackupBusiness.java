package top.ybgnb.bilibili.backup.app.business.impl;

import lombok.extern.slf4j.Slf4j;
import top.ybgnb.bilibili.backup.app.bean.SavedUser;
import top.ybgnb.bilibili.backup.app.business.BaseBusinessForLoginUser;
import top.ybgnb.bilibili.backup.app.constant.AppConstant;
import top.ybgnb.bilibili.backup.app.menu.BackupRestoreMenu;
import top.ybgnb.bilibili.backup.biliapi.bean.Upper;
import top.ybgnb.bilibili.backup.biliapi.error.BusinessException;
import top.ybgnb.bilibili.backup.biliapi.service.ServiceBuilder;
import top.ybgnb.bilibili.backup.biliapi.user.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @ClassName BackupBusiness
 * @Description 备份
 * @Author hzhilong
 * @Time 2024/11/22
 * @Version 1.0
 */
@Slf4j
public class BackupBusiness extends BaseBusinessForLoginUser {

    @Override
    public Upper process(Scanner scanner) throws BusinessException {
        // 1. 登录/选择用户
        SavedUser user = super.chooseUser(scanner);
        // 2. 选择操作项目
        List<ServiceBuilder> serviceItems = BackupRestoreMenu.chooseServiceItems(scanner);
        // 3. 设置当前备份目录
        String path = String.format("%s%s_%s_%s/", AppConstant.BACKUP_PATH_PREFIX, user.getName(), user.getMid(),
                new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date()));
        // 4. 执行各个项目
        for (ServiceBuilder item : serviceItems) {
            try {
                item.build(client, new User(user.getCookie()), path).backup();
            } catch (BusinessException ex) {
                log.info("操作失败，{}\n", ex.getMessage());
            }
        }
        return user;
    }

}
