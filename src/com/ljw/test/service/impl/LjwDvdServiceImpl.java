package com.ljw.test.service.impl;

import com.ljw.base.ioc.annotation.Service;
import com.ljw.base.util.CollectionUtil;
import com.ljw.base.util.DateUtil;
import com.ljw.test.dao.LjwDvdDao;
import com.ljw.test.dao.LjwLendDao;
import com.ljw.test.dao.LjwTypeDao;
import com.ljw.test.enums.LendStatusEnum;
import com.ljw.test.pojo.LjwDvd;
import com.ljw.test.pojo.LjwLend;
import com.ljw.test.pojo.LjwType;
import com.ljw.test.service.LjwDvdService;
import com.ljw.test.service.TestService;
import com.ljw.test.vo.DvdVO;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/10/30 17:12
 */
@Service
public class LjwDvdServiceImpl implements LjwDvdService {
    @Resource
    private LjwDvdDao dvdDao;
    //private LjwDvdDao dvdDao = (LjwDvdDao) PropertiesUtil.getObject("LJW_DVD_DAO");
    @Resource
    private LjwLendDao lendDao;
    @Resource
    private LjwTypeDao typeDao;
    @Resource
    private TestService testService;

    @Override
    public void test() {
        System.out.println("im dvd");
        testService.sy();
    }

    @Override
    public boolean addDVD(LjwDvd dvd) {
        return dvdDao.addDVD(dvd) == 1;
    }

    @Override
    public List<DvdVO> showAllDVD() {
        List<DvdVO> list = dvdDao.queryDVDList();
        if (CollectionUtil.isEmpty(list)) {
            System.out.println("没有查询出DVD记录");
        } else {
            System.out.println(CollectionUtil.filterStr("\t\t", "序号", "状态", "名称", "借出日期"));
            for (DvdVO dvd : list) {
                System.out.println(CollectionUtil.filterStr("\t\t", dvd.getId(), LendStatusEnum.getEnumByCode(dvd.getStatus()), dvd.getName(), dvd.getLendDate()));
            }
        }
        return list;
    }

    @Override
    public boolean delete(Long id) {
        ArrayList<Long> list = new ArrayList<>();
        list.add(id);
        return dvdDao.deleteDVD(list) > 0;
    }

    @Override
    public boolean lendDVD(Long id) {
        LjwDvd dvd = dvdDao.getDVDById(id);
        if (dvd == null) {
            System.out.println("没有查询到此编号的DVD");
            return false;
        }
        if (dvd.getStatus() != LendStatusEnum.NO.getCode()) {
            System.out.println("此DVD已借出,不可借");
            return false;
        }
        dvd.setStatus(LendStatusEnum.YES.getCode());
        dvd.setCount(dvd.getCount() + 1);
        if (dvdDao.updateDVD(dvd) > 0) {
            if (lendDao.addLendDVD(id) > 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean returnDVD(Long id, String date) {
        LjwLend lend = lendDao.getLendByDvdId(id);
        if (lend == null) {
            System.out.println("没有查询到此dvd的借出记录");
            return false;
        }
        LjwDvd dvd = dvdDao.getDVDById(id);
        dvd.setStatus(LendStatusEnum.NO.getCode());
        if (dvdDao.updateDVD(dvd) > 0) {
            lend.setReturnDate(date);
            Integer days = DateUtil.differentDaysByString(lend.getLendDate(), lend.getReturnDate());
            if (days == null) {
                return false;
            }
            lend.setMoney(days);
            if (lendDao.update(lend) > 0) {
                System.out.println("归还成功,需支付" + days + "元");
                return true;
            }
        }
        return false;
    }

    /**
     * 获取类型Map
     */
    private Map<Long, String> getTypeMap() {
        Map<Long, String> map = new HashMap<>();
        List<LjwType> typeList = typeDao.getTypeList();
        if (!CollectionUtil.isEmpty(typeList)) {
            for (LjwType type : typeList) {
                map.put(type.getId(), type.getName());
            }
        }
        return map;
    }
}
