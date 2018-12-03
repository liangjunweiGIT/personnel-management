package com.ljw.test.dao.impl;

import com.ljw.base.db.BaseDao;
import com.ljw.base.ioc.annotation.Bean;
import com.ljw.base.util.DateUtil;
import com.ljw.test.dao.LjwLendDao;
import com.ljw.test.pojo.LjwLend;

import java.util.UUID;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/10/30 18:22
 */
@Bean
public class LjwLendDaoImpl extends BaseDao implements LjwLendDao {
    @Override
    public int addLendDVD(Long id) {
        LjwLend lend = new LjwLend();
        lend.setDvdId(id);
        lend.setLendDate(DateUtil.getNowDateStr());
        return insert("INSERT INTO t_lend(dvd_id,lend_date) VALUES(#dvdId#,#lendDate#)", lend);
    }

    @Override
    public int update(LjwLend lend) {
        return update("update t_lend set return_date = #returnDate#,money = #money# where id = #id#", lend);
    }

    @Override
    public LjwLend getLendByDvdId(Long dvdId) {
        return queryForObject("select * from t_lend where dvd_id=#dvdId# AND return_date=NULL ", LjwLend.class, dvdId);
    }
}
