package com.salton123.eleph.video.compressor.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Objects;

/**
 * Time:2022/1/27 14:59
 * Author:wujinsheng1
 * Description:
 */
@Table(name = "video", onCreated = "CREATE UNIQUE INDEX index_name ON video(id,filePath)")
public class VideoItem implements Serializable {
  @Column(name = "id", isId = true) private int id;
  @Column(name = "filePath")  public String filePath = "";
  @Column(name = "dirName")  public String dirName = "";
  @Column(name = "name")  public String name = "";
  @Column(name = "mimeType")  public String mimeType = "";
  @Column(name = "size")  public long size = 0;
  @Column(name = "width")  public int width = 0;
  @Column(name = "height")  public int height = 0;
  @Column(name = "duration")  public long duration = 0;
  @Column(name = "createdAt")  public long createdAt = 0;
  @Column(name = "updatedAt")  public long updatedAt = 0;
  @Column(name = "playedPosition")  public long playedPosition = 0;
  @Column(name = "letter")  public String letter = "";
  @Column(name = "dateTime")  public long dateTime = 0;
  @Column(name = "squeezeProgress")  public int squeezeProgress = 0;
  @Column(name = "squeezeState") public int squeezeState = 0;   //0 default 1 ing 2 ed
  public VideoItem() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    VideoItem videoItem = (VideoItem) o;
    return filePath.equals(videoItem.filePath);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filePath);
  }

  @Override
  public String toString() {
    return "VideoItem{" +
        "name='" + name + '\'' +
        '}';
  }
}
