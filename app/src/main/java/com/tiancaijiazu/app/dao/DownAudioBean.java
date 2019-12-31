package com.tiancaijiazu.app.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity
public class DownAudioBean implements Serializable {
    static final long serialVersionUID = -15515456L;
    @Id(autoincrement = true)
    Long id;

    String songId;

    String songUrl;

    String songCover;

    String songName;

    String description;

    @Generated(hash = 887852830)
    public DownAudioBean(Long id, String songId, String songUrl, String songCover,
            String songName, String description) {
        this.id = id;
        this.songId = songId;
        this.songUrl = songUrl;
        this.songCover = songCover;
        this.songName = songName;
        this.description = description;
    }

    @Generated(hash = 1058086877)
    public DownAudioBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSongId() {
        return this.songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getSongUrl() {
        return this.songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getSongCover() {
        return this.songCover;
    }

    public void setSongCover(String songCover) {
        this.songCover = songCover;
    }

    public String getSongName() {
        return this.songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
}
