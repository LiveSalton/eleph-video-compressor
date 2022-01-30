package com.salton123.eleph.video.compressor.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Time:2022/1/27 14:59
 * Author:wujinsheng1
 * Description:
 */
@Table(name = "video", onCreated = "CREATE UNIQUE INDEX index_name ON video(id,filePath)")
public class VideoItem {
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
  @Column(name = "compressProgress")  public int compressProgress = 0;

  public VideoItem() {
  }

  public VideoItem(String filePath, String dirName, String name, String mimeType, long size, int width, int height,
                   long duration, long createdAt, long updatedAt, long playedPosition, String letter,
                   int compressProgress) {
    this.filePath = filePath;
    this.dirName = dirName;
    this.name = name;
    this.mimeType = mimeType;
    this.size = size;
    this.width = width;
    this.height = height;
    this.duration = duration;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.playedPosition = playedPosition;
    this.letter = letter;
    this.compressProgress = compressProgress;
  }

  @Override
  public String toString() {
    return "VideoItem{" +
        "name='" + name + '\'' +
        '}';
  }
}
