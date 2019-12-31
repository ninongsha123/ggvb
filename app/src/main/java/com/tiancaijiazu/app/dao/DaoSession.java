package com.tiancaijiazu.app.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.tiancaijiazu.app.dao.DownAudioBean;
import com.tiancaijiazu.app.dao.HistoryTitleBean;
import com.tiancaijiazu.app.dao.TheTopicRecordBean;
import com.tiancaijiazu.app.dao.ToKenDaoBean;
import com.tiancaijiazu.app.dao.UserInFo;

import com.tiancaijiazu.app.dao.DownAudioBeanDao;
import com.tiancaijiazu.app.dao.HistoryTitleBeanDao;
import com.tiancaijiazu.app.dao.TheTopicRecordBeanDao;
import com.tiancaijiazu.app.dao.ToKenDaoBeanDao;
import com.tiancaijiazu.app.dao.UserInFoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig downAudioBeanDaoConfig;
    private final DaoConfig historyTitleBeanDaoConfig;
    private final DaoConfig theTopicRecordBeanDaoConfig;
    private final DaoConfig toKenDaoBeanDaoConfig;
    private final DaoConfig userInFoDaoConfig;

    private final DownAudioBeanDao downAudioBeanDao;
    private final HistoryTitleBeanDao historyTitleBeanDao;
    private final TheTopicRecordBeanDao theTopicRecordBeanDao;
    private final ToKenDaoBeanDao toKenDaoBeanDao;
    private final UserInFoDao userInFoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        downAudioBeanDaoConfig = daoConfigMap.get(DownAudioBeanDao.class).clone();
        downAudioBeanDaoConfig.initIdentityScope(type);

        historyTitleBeanDaoConfig = daoConfigMap.get(HistoryTitleBeanDao.class).clone();
        historyTitleBeanDaoConfig.initIdentityScope(type);

        theTopicRecordBeanDaoConfig = daoConfigMap.get(TheTopicRecordBeanDao.class).clone();
        theTopicRecordBeanDaoConfig.initIdentityScope(type);

        toKenDaoBeanDaoConfig = daoConfigMap.get(ToKenDaoBeanDao.class).clone();
        toKenDaoBeanDaoConfig.initIdentityScope(type);

        userInFoDaoConfig = daoConfigMap.get(UserInFoDao.class).clone();
        userInFoDaoConfig.initIdentityScope(type);

        downAudioBeanDao = new DownAudioBeanDao(downAudioBeanDaoConfig, this);
        historyTitleBeanDao = new HistoryTitleBeanDao(historyTitleBeanDaoConfig, this);
        theTopicRecordBeanDao = new TheTopicRecordBeanDao(theTopicRecordBeanDaoConfig, this);
        toKenDaoBeanDao = new ToKenDaoBeanDao(toKenDaoBeanDaoConfig, this);
        userInFoDao = new UserInFoDao(userInFoDaoConfig, this);

        registerDao(DownAudioBean.class, downAudioBeanDao);
        registerDao(HistoryTitleBean.class, historyTitleBeanDao);
        registerDao(TheTopicRecordBean.class, theTopicRecordBeanDao);
        registerDao(ToKenDaoBean.class, toKenDaoBeanDao);
        registerDao(UserInFo.class, userInFoDao);
    }
    
    public void clear() {
        downAudioBeanDaoConfig.clearIdentityScope();
        historyTitleBeanDaoConfig.clearIdentityScope();
        theTopicRecordBeanDaoConfig.clearIdentityScope();
        toKenDaoBeanDaoConfig.clearIdentityScope();
        userInFoDaoConfig.clearIdentityScope();
    }

    public DownAudioBeanDao getDownAudioBeanDao() {
        return downAudioBeanDao;
    }

    public HistoryTitleBeanDao getHistoryTitleBeanDao() {
        return historyTitleBeanDao;
    }

    public TheTopicRecordBeanDao getTheTopicRecordBeanDao() {
        return theTopicRecordBeanDao;
    }

    public ToKenDaoBeanDao getToKenDaoBeanDao() {
        return toKenDaoBeanDao;
    }

    public UserInFoDao getUserInFoDao() {
        return userInFoDao;
    }

}