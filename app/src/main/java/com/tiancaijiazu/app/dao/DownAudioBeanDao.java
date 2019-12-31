package com.tiancaijiazu.app.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DOWN_AUDIO_BEAN".
*/
public class DownAudioBeanDao extends AbstractDao<DownAudioBean, Long> {

    public static final String TABLENAME = "DOWN_AUDIO_BEAN";

    /**
     * Properties of entity DownAudioBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property SongId = new Property(1, String.class, "songId", false, "SONG_ID");
        public final static Property SongUrl = new Property(2, String.class, "songUrl", false, "SONG_URL");
        public final static Property SongCover = new Property(3, String.class, "songCover", false, "SONG_COVER");
        public final static Property SongName = new Property(4, String.class, "songName", false, "SONG_NAME");
        public final static Property Description = new Property(5, String.class, "description", false, "DESCRIPTION");
    }


    public DownAudioBeanDao(DaoConfig config) {
        super(config);
    }
    
    public DownAudioBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DOWN_AUDIO_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"SONG_ID\" TEXT," + // 1: songId
                "\"SONG_URL\" TEXT," + // 2: songUrl
                "\"SONG_COVER\" TEXT," + // 3: songCover
                "\"SONG_NAME\" TEXT," + // 4: songName
                "\"DESCRIPTION\" TEXT);"); // 5: description
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DOWN_AUDIO_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DownAudioBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String songId = entity.getSongId();
        if (songId != null) {
            stmt.bindString(2, songId);
        }
 
        String songUrl = entity.getSongUrl();
        if (songUrl != null) {
            stmt.bindString(3, songUrl);
        }
 
        String songCover = entity.getSongCover();
        if (songCover != null) {
            stmt.bindString(4, songCover);
        }
 
        String songName = entity.getSongName();
        if (songName != null) {
            stmt.bindString(5, songName);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(6, description);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DownAudioBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String songId = entity.getSongId();
        if (songId != null) {
            stmt.bindString(2, songId);
        }
 
        String songUrl = entity.getSongUrl();
        if (songUrl != null) {
            stmt.bindString(3, songUrl);
        }
 
        String songCover = entity.getSongCover();
        if (songCover != null) {
            stmt.bindString(4, songCover);
        }
 
        String songName = entity.getSongName();
        if (songName != null) {
            stmt.bindString(5, songName);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(6, description);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DownAudioBean readEntity(Cursor cursor, int offset) {
        DownAudioBean entity = new DownAudioBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // songId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // songUrl
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // songCover
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // songName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // description
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DownAudioBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSongId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSongUrl(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSongCover(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSongName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDescription(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DownAudioBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DownAudioBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DownAudioBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}