package com.dada.basic.service.dada;

import com.dada.basic.core.entity.dada.AreaBean;
import com.dada.basic.core.entity.dada.example.AreaExample;
import com.dada.basic.dao.dada.AreaDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by cook on 16/9/21.
 */
public class AreaService {

    @Autowired
    private AreaDao areaDao;

    public AreaBean getById(Long areaId) {
        return areaDao.getById(areaId);
    }

    public List<AreaBean> findAreas(String areaName) {
        AreaExample exam = new AreaExample();
        exam.createCriteria().andNameLike("%"+areaName+"%");
        return areaDao.find(exam);
    }

    public int addArea(AreaBean area) {
        return areaDao.save(area);
    }

}
