package com.onlineSchool.eduService.service;

import com.onlineSchool.eduService.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.onlineSchool.eduService.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author guo
 * @since 2023-02-04
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> queryChapterAndVideo(String courseId);

    boolean removeChapterById(String id);
}
