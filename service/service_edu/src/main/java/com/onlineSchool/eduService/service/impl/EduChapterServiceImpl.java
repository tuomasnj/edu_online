package com.onlineSchool.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onlineSchool.eduService.entity.EduChapter;
import com.onlineSchool.eduService.entity.EduVideo;
import com.onlineSchool.eduService.entity.vo.ChapterVo;
import com.onlineSchool.eduService.entity.vo.VideoVo;
import com.onlineSchool.eduService.mapper.EduChapterMapper;
import com.onlineSchool.eduService.mapper.EduVideoMapper;
import com.onlineSchool.eduService.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onlineSchool.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author guo
 * @since 2023-02-04
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    EduChapterMapper eduChapterMapper;

    @Autowired
    EduVideoMapper eduVideoMapper;

    @Override
    public List<ChapterVo> queryChapterAndVideo(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.orderByAsc("sort");
        List<EduChapter> chapters = eduChapterMapper.selectList(wrapper);
        List<ChapterVo> chapterVos = new ArrayList<>();
        ChapterVo chapterVo;
        for (EduChapter eduChapter: chapters) {
            chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            QueryWrapper<EduVideo> queryWrapper = new QueryWrapper();
            queryWrapper.eq("chapter_id", eduChapter.getId()).eq("course_id", eduChapter.getCourseId());
            queryWrapper.orderByAsc("sort");
            List<EduVideo> eduVideos = eduVideoMapper.selectList(queryWrapper);
            List<VideoVo> videoVos = new ArrayList<>();
            for(EduVideo eduVideo: eduVideos){
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(eduVideo, videoVo);
                videoVos.add(videoVo);
            }
            chapterVo.setChildren(videoVos);
            chapterVos.add(chapterVo);
        }
        return chapterVos;
    }

    @Override
    public boolean removeChapterById(String id) {
        QueryWrapper<EduVideo> wrapper =new QueryWrapper<>();
        wrapper.eq("chapter_id", id);
        Integer resultCount = eduVideoMapper.selectCount(wrapper);
        if(resultCount > 0){
            throw new GuliException(20001, "该章节中还有小节，不能删除");
        }else {
            int ans = eduChapterMapper.deleteById(id);
            return ans == 0? false : true;
        }
    }
}
