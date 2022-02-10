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
@Table(name = "video")
public class VideoItem implements Serializable {
  @Column(name = "id", isId = true) public int id;
  @Column(name = "filePath")  public String filePath = "";
  @Column(name = "name")  public String name = "";
  @Column(name = "mimeType")  public String mimeType = "";
  @Column(name = "size")  public long size = 0;
  @Column(name = "width")  public int width = 0;
  @Column(name = "height")  public int height = 0;
  @Column(name = "duration")  public long duration = 0;
  @Column(name = "createdAt")  public long createdAt = 0;
  @Column(name = "dateTime")  public long dateTime = 0;
  @Column(name = "squeezeProgress")  public int squeezeProgress = 0;
  @Column(name = "squeezeState") public int squeezeState = 0;   //0 default 1 ing 2 success 3 failed
  @Column(name = "squeezeSavePath") public String squeezeSavePath = "";   //0 default 1 ing 2 success 3 failed
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
